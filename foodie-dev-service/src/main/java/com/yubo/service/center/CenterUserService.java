package com.yubo.service.center;

import com.yubo.pojo.Users;
import com.yubo.pojo.bo.center.CenterUserBO;

public interface CenterUserService {
    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    public Users queryUserInfo(String userId);

    /**
     * 修改用户信息
     * @param userId
     * @param centerUserBO
     */
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO);

}
