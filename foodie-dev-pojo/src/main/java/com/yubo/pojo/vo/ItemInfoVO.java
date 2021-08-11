package com.yubo.pojo.vo;

import com.yubo.pojo.Items;
import com.yubo.pojo.ItemsImg;
import com.yubo.pojo.ItemsParam;
import com.yubo.pojo.ItemsSpec;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**商品详情*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfoVO {
    private Items item ;
    private List<ItemsImg> itemImgList ;
    private ItemsParam itemParams ;
    private List<ItemsSpec> itemSpecList;


}
