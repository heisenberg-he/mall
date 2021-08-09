package com.yubo.service;

import com.yubo.pojo.Users;
import com.yubo.pojo.bo.UserBO;

public interface UserService {
    /**
     * 判断用户名是否存在
     */
    public Boolean queryUserNameIsExist(String username);

    /**
     * 创建新用户
     */
    public Users createUser(UserBO userBO);


    /**
     * 用户登陆
     */
    public Users UserLogin(UserBO userBO);

}
