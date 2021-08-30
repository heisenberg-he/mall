package com.yubo.service.Impl.center;

import com.yubo.mapper.UsersMapper;
import com.yubo.pojo.Users;
import com.yubo.pojo.bo.center.CenterUserBO;
import com.yubo.service.center.CenterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CenterUserServiceImpl implements CenterUserService {

    @Autowired
    public UsersMapper usersMapper;

    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }


    /**
     * 修改用户信息
     * @param userId
     * @param centerUserBO
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {
        Users updateUser = new Users();
        BeanUtils.copyProperties(centerUserBO, updateUser);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(updateUser);
        return queryUserInfo(userId);
    }
}
