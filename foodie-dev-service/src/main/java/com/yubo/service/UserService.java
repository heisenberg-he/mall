package com.yubo.service;

public interface UserService {
    /**
     * 判断用户名是否存在
     */
    public Boolean queryUserNameIsExist(String username);
}
