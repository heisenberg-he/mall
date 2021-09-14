package com.yubo.controller;

import com.yubo.pojo.Carousel;
import com.yubo.pojo.Category;
import com.yubo.pojo.vo.CategoryVO;
import com.yubo.pojo.vo.NewItemsVO;
import com.yubo.service.CarouselService;
import com.yubo.service.CategoryService;
import com.yubo.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商城首页")
@RestController
@RequestMapping("index")
public class IndexController {
        @Autowired
        private CarouselService carouselService;

        @Autowired
        private CategoryService categoryService;

        @ApiOperation(value = "获取首页轮播图",notes = "获取首页轮播图",httpMethod = "GET")
        @GetMapping("/carousel")
        public IMOOCJSONResult getAll(){
            List<Carousel> carouselList = carouselService.getAll();
            return IMOOCJSONResult.ok(carouselList);
        }

        /**
         * 首页分类展示需求：
         * 1. 第一次刷新主页查询大分类，渲染展示到首页
         * 2. 如果鼠标上移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
         */
        @ApiOperation(value = "加载商品分类",notes = "加载商品分类",httpMethod = "GET")
        @GetMapping("/cats")
        public IMOOCJSONResult getRootCats(){
            List<Category> categorys = categoryService.getRootCategorys();
            return IMOOCJSONResult.ok(categorys);
        }


        @ApiOperation(value = "获取二级分类",notes = "获取二级分类",httpMethod = "GET")
        @GetMapping("/subCat/{rootCatId}")
        public IMOOCJSONResult getSubCategory(
                @ApiParam(value = "一级分类Id",name = "rootCatId",required = true)
                @PathVariable Integer rootCatId){
            if (StringUtils.isEmpty(rootCatId)){
                return IMOOCJSONResult.errorMsg("参数不能为空");
            }
            List<CategoryVO> subCategorys = categoryService.getSubCategory(rootCatId);
            return IMOOCJSONResult.ok(subCategorys);
        }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public IMOOCJSONResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {

        if (rootCatId == null) {
            return IMOOCJSONResult.errorMsg("分类不存在");
        }

        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);
        return IMOOCJSONResult.ok(list);
    }


}
