package com.yubo.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yubo.enums.CommentLevel;
import com.yubo.enums.YesOrNo;
import com.yubo.mapper.*;
import com.yubo.pojo.*;
import com.yubo.pojo.vo.CommentLevelCountsVO;
import com.yubo.pojo.vo.ItemCommentVO;
import com.yubo.pojo.vo.SearchItemsVO;
import com.yubo.pojo.vo.ShopcartVO;
import com.yubo.service.ItemService;
import com.yubo.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class ItemServiceImpl  implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    /**
     * 根据商品ID查询详情
     * @param itemId
     * @return
     */
    @Override
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    /**
     * 根据商品id查询商品图片列表
     * @param itemId
     * @return
     */
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria itemCriteria = example.createCriteria();
        itemCriteria.andEqualTo("itemId",itemId);
        return itemsImgMapper.selectByExample(example);
    }

    /**
     * 根据商品id查询商品规格
     * @param itemId
     * @return
     */
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria itemCriteria = example.createCriteria();
        itemCriteria.andEqualTo("itemId",itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    /**
     * 根据商品id查询商品参数
     * @param itemId
     * @return
     */
    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria itemCriteria = example.createCriteria();
        itemCriteria.andEqualTo("itemId",itemId);
        return itemsParamMapper.selectOneByExample(example);
    }


    /**
     * 根据商品id查询商品评价
     * @param itemId
     * @return
     */
    @Override
    public CommentLevelCountsVO queryItemComment(String itemId) {
        ItemsComments itemsComments = new ItemsComments();
        itemsComments.setItemId(itemId);
        itemsComments.setCommentLevel(CommentLevel.GOOD.type);/**查询好评数量*/
        int goodComment = itemsCommentsMapper.selectCount(itemsComments);
        itemsComments.setCommentLevel(CommentLevel.GOOD.type);/**查询中评数量*/
        int normalCounts = itemsCommentsMapper.selectCount(itemsComments);
        itemsComments.setCommentLevel(CommentLevel.GOOD.type);/**查询差评数量*/
        int badComment = itemsCommentsMapper.selectCount(itemsComments);

        /**评价总数*/
        int totalCounts = goodComment + badComment + normalCounts;
        return new CommentLevelCountsVO(totalCounts,goodComment,normalCounts,badComment);
    }

    /**
     * 根据商品id查询商品评价（分页）
     * @param itemId
     * @param level
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {

        Example example  = new Example(ItemsComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        if(level != null){
            criteria.andEqualTo("commentLevel",level);
        }
        PageHelper.startPage(page,pageSize);
        List<ItemsComments> itemsComments = itemsCommentsMapper.selectByExample(example);
        //封装分页模型
        PageInfo<ItemsComments> pageInfo = new PageInfo<>(itemsComments);
        PagedGridResult pagedGridResult = new PagedGridResult();
        pagedGridResult.setPage(pageInfo.getPageNum());
        pagedGridResult.setTotal(pageInfo.getPages());
        pagedGridResult.setRecords(pageInfo.getTotal());
        pagedGridResult.setRows(itemsComments);
        return  pagedGridResult;
    }

    /**
     * 搜索商品列表
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PagedGridResult searhItems(String keywords, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> list = itemsMapper.searchItems(map);

        return setterPagedGrid(list, page);
    }

    private PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }

    /**
     * 搜索商品列表（根据三级分类）
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PagedGridResult searhItems(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("catId", catId);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> list = itemsMapper.searchItemsByThirdCat(map);

        return setterPagedGrid(list, page);
    }

    /**
     * 根据商品ID获取评价
     * @param
     * @return
     */
    public  List<ItemCommentVO> queryItemsComments(String itemId,Integer level){
        Map<String, Object> map = new HashMap<>();
        map.put("itemId",itemId);
        map.put("level",level);
        List<ItemCommentVO> itemCommentVOS = itemsMapper.queryItemsComments(map);
        return  itemCommentVOS;
    }



    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ShopcartVO> queryItemsBySpecIds(String specIds) {

        String ids[] = specIds.split(",");
        List<String> specIdsList = new ArrayList<>();
        Collections.addAll(specIdsList, ids);

        return itemsMapper.queryItemsBySpecIds(specIdsList);
    }

    /**
     * 根据规格ID查询规格的具体信息，主要获取价格
     * @param itemSpecId
     * @return
     */
    @Override
    public ItemsSpec queryItemSpecById(String itemSpecId) {
        return itemsSpecMapper.selectByPrimaryKey(itemSpecId);
    }


    /**
     * 根据商品id获得商品图片主图url
     * @param itemId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String queryItemMainImgById(String itemId) {
        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setItemId(itemId);
        itemsImg.setIsMain(YesOrNo.YES.type);
        ItemsImg result = itemsImgMapper.selectOne(itemsImg);
        return result != null ? result.getUrl() : "";
    }


    /**
     * 减少库存
     * @param specId
     * @param buyCounts
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void decreaseItemSpecStock(String specId, int buyCounts) {

        // synchronized 不推荐使用，集群下无用，性能低下
        // 锁数据库: 不推荐，导致数据库性能低下
        // 分布式锁 zookeeper redis

        // lockUtil.getLock(); -- 加锁

        // 1. 查询库存
//        int stock = 10;

        // 2. 判断库存，是否能够减少到0以下
//        if (stock - buyCounts < 0) {
        // 提示用户库存不够
//            10 - 3 -3 - 5 = -1
//        }

        // lockUtil.unLock(); -- 解锁


        int result = itemsMapper.decreaseItemSpecStock(specId, buyCounts);
        if (result != 1) {
            throw new RuntimeException("订单创建失败，原因：库存不足!");
        }
    }

}
