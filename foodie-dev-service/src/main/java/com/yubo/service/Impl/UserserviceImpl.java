package com.yubo.service.Impl;

import com.yubo.mapper.UsersMapper;
import com.yubo.pojo.Users;
import com.yubo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserserviceImpl  implements UserService {
    @Autowired
    private UsersMapper usersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Boolean queryUserNameIsExist(String username) {
        Example userExample = new Example(Users.class);
        Example.Criteria userExampleCriteria = userExample.createCriteria();
        userExampleCriteria.andEqualTo("username", username);
        Users result = usersMapper.selectOneByExample(userExample);
        return result == null ? false : true;
    }
}
