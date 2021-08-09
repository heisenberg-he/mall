package com.yubo.service.Impl;

import com.yubo.enums.Sex;
import com.yubo.mapper.UsersMapper;
import com.yubo.pojo.Users;
import com.yubo.pojo.bo.UserBO;
import com.yubo.service.UserService;
import com.yubo.utils.DateUtil;
import com.yubo.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserserviceImpl  implements UserService {
    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    /**用户头像（默认）*/
    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";
    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Boolean queryUserNameIsExist(String username) {
        Example userExample = new Example(Users.class);
        Example.Criteria userExampleCriteria = userExample.createCriteria();
        userExampleCriteria.andEqualTo("username", username);
        Users result = usersMapper.selectOneByExample(userExample);
        return result == null ? false : true;
    }


    /**
     * 用户注册
     * @param userBO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) {
        Users  user = new Users();
        String userId = sid.nextShort();
        user.setId(userId);
        user.setUsername(userBO.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 默认用户昵称同用户名
        user.setNickname(userBO.getUsername());
        // 默认头像
        user.setFace(USER_FACE);
        // 默认生日
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        // 默认性别为 保密
        user.setSex(Sex.secret.type);

        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        usersMapper.insert(user);

        return null ;
    }

    /**
     * 用户登陆
     * @param userBO
     * @return
     */
    @Override
    public Users UserLogin(UserBO userBO) {
        Example userExample = new Example(Users.class);
        try {
            Example.Criteria userExampleCriteria = userExample.createCriteria();
            userExampleCriteria.andEqualTo("username", userBO.getUsername());
            userExampleCriteria.andEqualTo("password", MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usersMapper.selectOneByExample(userExample);
    }
}
