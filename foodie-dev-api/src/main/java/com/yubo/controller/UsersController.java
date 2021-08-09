package com.yubo.controller;

import com.yubo.pojo.Users;
import com.yubo.pojo.bo.UserBO;
import com.yubo.service.UserService;
import com.yubo.utils.CookieUtils;
import com.yubo.utils.IMOOCJSONResult;
import com.yubo.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "用户登陆注册")
@RestController
@RequestMapping("/passport")
public class UsersController {
    @Autowired
    private UserService userService;

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
    public IMOOCJSONResult createUser(@RequestBody UserBO userBO){
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

        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据

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
        userLogin = setNullProperty(userLogin);

        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userLogin), true);

        return new IMOOCJSONResult(userLogin);
    }

    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(@RequestParam String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        // 清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");

        // TODO 用户退出登录，需要清空购物车
        // TODO 分布式会话中需要清除用户数据

        return IMOOCJSONResult.ok();
    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }
}
