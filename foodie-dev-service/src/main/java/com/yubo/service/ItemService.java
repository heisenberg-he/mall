package com.yubo.service;

import com.yubo.pojo.Items;
import com.yubo.pojo.ItemsImg;
import com.yubo.pojo.ItemsParam;
import com.yubo.pojo.ItemsSpec;
import com.yubo.pojo.vo.CommentLevelCountsVO;
import com.yubo.utils.PagedGridResult;

import java.util.List;

public interface ItemService {
    /**
     * 根据商品ID查询详情
     * @param itemId
     * @return
     */
    public Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品图片列表
     * @param itemId
     * @return
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格
     * @param itemId
     * @return
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     * @param itemId
     * @return
     */
    public ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询商品评价
     * @param itemId
     * @return
     */
    public CommentLevelCountsVO queryItemComment(String itemId);

    /**
     * 根据商品id查询商品评价（分页）
     * @param itemId
     * @param level
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);
}