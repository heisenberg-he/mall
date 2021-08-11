package com.yubo.service.Impl;

import com.yubo.mapper.CategoryMapper;
import com.yubo.pojo.Category;
import com.yubo.pojo.vo.CategoryVO;
import com.yubo.pojo.vo.NewItemsVO;
import com.yubo.pojo.vo.SubCategoryVO;
import com.yubo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategroyServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取商品一级分类
     * @return
     */
    @Override
    public List<Category> getRootCategorys() {
        Example example = new Example(Category.class);
        example.orderBy("type").asc();
        Example.Criteria category = example.createCriteria();
        category.andEqualTo("type",1);
        List<Category> categories = categoryMapper.selectByExample(example);
        return  categories;
    }

    /**
     * 获取商品二级分类
     * @return
     */
    @Override
    public List<CategoryVO> getSubCategory(Integer rootCatId) {
        List<CategoryVO> categoryList = categoryMapper.getCategoryList(rootCatId);
        return categoryList;
    }

    /**
     * 查询首页每个一级分类下的6条最新商品数据
     * @param rootCatId
     * @return
     */
    @Override
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {
        List<NewItemsVO> sixNewItemsLazy = categoryMapper.getSixNewItemsLazy(rootCatId);
        return sixNewItemsLazy;
    }
}
