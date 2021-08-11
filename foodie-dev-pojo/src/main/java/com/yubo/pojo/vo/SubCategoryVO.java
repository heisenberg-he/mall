package com.yubo.pojo.vo;

import lombok.Data;

/**三级分类实体类*/
@Data
public class SubCategoryVO {
    private Integer subId;
    private String subName;
    private String subType;
    private Integer subFatherId;

}
