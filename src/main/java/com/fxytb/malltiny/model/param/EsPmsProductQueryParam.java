package com.fxytb.malltiny.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Es商品查询参数", description = "Es商品查询参数")
public class EsPmsProductQueryParam {

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "子标题")
    private String subTitle;

    @ApiModelProperty(value = "商品名称")
    private String name;

}
