package com.yubo.mapper;

import com.yubo.my.mapper.MyMapper;
import com.yubo.pojo.Items;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemsMapper extends MyMapper<Items> {
}