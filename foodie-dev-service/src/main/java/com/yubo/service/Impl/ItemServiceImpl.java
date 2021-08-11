package com.yubo.service.Impl;

import com.yubo.mapper.ItemsImgMapper;
import com.yubo.mapper.ItemsMapper;
import com.yubo.mapper.ItemsParamMapper;
import com.yubo.mapper.ItemsSpecMapper;
import com.yubo.pojo.Items;
import com.yubo.pojo.ItemsImg;
import com.yubo.pojo.ItemsParam;
import com.yubo.pojo.ItemsSpec;
import com.yubo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

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
}
