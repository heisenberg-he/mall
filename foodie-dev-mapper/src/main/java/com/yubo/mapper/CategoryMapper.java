package com.yubo.mapper;

import com.yubo.my.mapper.MyMapper;
import com.yubo.pojo.Category;
import com.yubo.pojo.vo.CategoryVO;
import com.yubo.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper extends MyMapper<Category> {
    /**
     * 根据一级分类id查询子分类信息
     * @param rootCatId
     * @return
     */
    public List<CategoryVO> getCategoryList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的6条最新商品数据
     * @param rootCatId
     * @return
     */
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}