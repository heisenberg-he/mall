package com.yubo.mapper;

import com.yubo.my.mapper.MyMapper;
import com.yubo.pojo.UserAddress;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAddressMapper extends MyMapper<UserAddress> {
}