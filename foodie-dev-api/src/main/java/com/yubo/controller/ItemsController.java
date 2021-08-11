package com.yubo.controller;

import com.yubo.pojo.Items;
import com.yubo.pojo.ItemsImg;
import com.yubo.pojo.ItemsParam;
import com.yubo.pojo.ItemsSpec;
import com.yubo.pojo.vo.ItemInfoVO;
import com.yubo.service.ItemService;
import com.yubo.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "商品详情页")
@RestController
@RequestMapping("items")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情",notes = "查询商品详情",httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public IMOOCJSONResult getItemDetail(@PathVariable("itemId") String itemId){
        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgs = itemService.queryItemImgList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        List<ItemsSpec> itemsSpecs = itemService.queryItemSpecList(itemId);
        ItemInfoVO itemInfoVO = new ItemInfoVO(item,itemsImgs,itemsParam,itemsSpecs);
        return IMOOCJSONResult.ok(itemInfoVO);
    }
}
