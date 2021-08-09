package com.yubo.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户注册提交的参数
 */
@Data
@ApiModel(value = "用户对象BO",description = "从客户端传入的数据封装在你这个实体类中")
public class UserBO {
    @ApiModelProperty(value = "用户名",name = "username",required = true)
    private String username;
    @ApiModelProperty(value = "密码",name = "password",required = true)
    private String password;
    @ApiModelProperty(value = "确认密码",name = "confirmPassword",required = false)
    private String confirmPassword;
}
