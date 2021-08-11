package com.yubo.service;

import com.yubo.pojo.Category;
import com.yubo.pojo.vo.CategoryVO;
import com.yubo.pojo.vo.NewItemsVO;

import java.util.List;

/**商品分类*/
public interface CategoryService {
    /**
     * 获取商品一级分类
     * @return
     */
    public List<Category> getRootCategorys();


    /**
     * 获取商品级分类
     * @return
     */
    public List<CategoryVO> getSubCategory(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的6条最新商品数据
     * @param rootCatId
     * @return
     */
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
