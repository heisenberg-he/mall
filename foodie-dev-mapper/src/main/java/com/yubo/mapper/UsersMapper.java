package com.yubo.mapper;

import com.yubo.my.mapper.MyMapper;
import com.yubo.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper extends MyMapper<Users> {
}