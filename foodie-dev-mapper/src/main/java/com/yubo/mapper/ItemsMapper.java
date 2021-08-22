package com.yubo.mapper;

import com.yubo.my.mapper.MyMapper;
import com.yubo.pojo.Items;
import com.yubo.pojo.vo.ItemCommentVO;
import com.yubo.pojo.vo.SearchItemsVO;
import com.yubo.pojo.vo.ShopcartVO;
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

    /**
     * 商品搜索（根据关键字）
     * @param map
     * @return
     */
    public List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);

    /**
     * 商品搜索(根据三级分类)
     * @param map
     * @return
     */
    public List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);

    /**
     * 刷新购物车
     * @param specIdsList
     * @return
     */
    public List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List specIdsList);


}