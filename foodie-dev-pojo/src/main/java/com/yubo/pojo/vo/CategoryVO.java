package com.yubo.pojo.vo;

import lombok.Data;

import java.util.List;

/**二级分类实体类*/
@Data
public class CategoryVO {
    private Integer id;
    private String name;
    private String type;
    private Integer fatherId;

    // 三级分类vo list
    private List<SubCategoryVO> subCatList;
}
