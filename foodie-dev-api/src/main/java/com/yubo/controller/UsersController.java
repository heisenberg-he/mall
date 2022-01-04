package com.yubo.controller;

import com.yubo.pojo.Users;
import com.yubo.pojo.bo.UserBO;
import com.yubo.pojo.vo.UsersVO;
import com.yubo.service.UserService;
import com.yubo.utils.CookieUtils;
import com.yubo.utils.IMOOCJSONResult;
import com.yubo.utils.JsonUtils;
import com.yubo.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Api(value = "用户登陆注册")
@RestController
@RequestMapping("/passport")
public class UsersController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisOperator redisOperator;

    private String str = "REDIS_USER_TOKEN";

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    @ApiOperation(value = "判断用户名是否存在",notes = "判断用户名是否存在",httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExists(@RequestParam("username") String username) {
         if(!StringUtils.hasText(username)){
             return   IMOOCJSONResult.errorMsg("用户名不能为空");
         }
         Boolean aBoolean = userService.queryUserNameIsExist(username);
         if(aBoolean){
             return IMOOCJSONResult.errorMsg("用户名已经存在");
         }
         return  IMOOCJSONResult.ok();
    }

    /**
     * 新用户注册
     * @return
     */
    @ApiOperation(value = "用户注册",notes = "用户注册",httpMethod = "POST")
    @PostMapping("/regist")
    public IMOOCJSONResult createUser(@RequestBody UserBO userBO,
                                      HttpServletRequest request,
                                      HttpServletResponse response){
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();

        // 0. 判断用户名和密码必须不为空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(confirmPwd)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }

        // 1. 查询用户名是否存在
        boolean isExist = userService.queryUserNameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }

        // 2. 密码长度不能少于6位
        if (password.length() < 6) {
            return IMOOCJSONResult.errorMsg("密码长度不能少于6");
        }

        // 3. 判断两次密码是否一致
        if (!password.equals(confirmPwd)) {
            return IMOOCJSONResult.errorMsg("两次密码输入不一致");
        }

        // 4. 实现注册
        Users userResult = userService.createUser(userBO);

        // 生成用户token，存入redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(str+":"+userResult.getId(),uniqueToken);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(userResult, usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        CookieUtils.setCookie(request,response,"user",JsonUtils.objectToJson(usersVO),true);
        return IMOOCJSONResult.ok();
    }


    /**
     * 用户登陆
     * @param userBO
     * @return
     */
    @ApiOperation(value = "用户登陆",notes = "用户登陆",httpMethod = "POST")
    @PostMapping("/login")
    public IMOOCJSONResult login(@RequestBody UserBO userBO,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        Users userLogin = userService.UserLogin(userBO);
        if(userLogin == null){
            return new IMOOCJSONResult("用户名或密码错误");
        }
        // 生成用户token，存入redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(str+":"+userLogin.getId(),uniqueToken);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(userLogin, usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(usersVO), true);

        return new IMOOCJSONResult(userLogin);
    }

    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(@RequestParam String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        // 清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");

        //  用户退出登录，需要清空购物车
        //  分布式会话中需要清除用户数据
        redisOperator.del(str+":"+userId);
        return IMOOCJSONResult.ok();
    }
}
