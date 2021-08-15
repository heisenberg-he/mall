package com.yubo.mapper;

import com.yubo.my.mapper.MyMapper;
import com.yubo.pojo.Items;
import com.yubo.pojo.vo.ItemCommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ItemsMapper extends MyMapper<Items> {
    /**
     * 根据商品ID获取评价
     * @param paramsMap
     * @return
     */
     public  List<ItemCommentVO> queryItemsComments(@Param("paramsMap") Map<String,Object> paramsMap);
}