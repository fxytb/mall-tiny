package com.fxytb.malltiny.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "品牌新增参数", description = "品牌新增参数")
public class PmsBrandAddParam {


    @NotBlank(message = "品牌名称不能为空")
    @ApiModelProperty(value = "品牌名称")
    private String name;

    @ApiModelProperty(value = "首字母")
    @NotBlank(message = "首字母不能为空")
    private String firstLetter;

    @ApiModelProperty(value = "品牌排序号")
    private Integer sort;

    @ApiModelProperty(value = "是否为品牌制造商：0->不是；1->是")
    private Integer factoryStatus;

    @ApiModelProperty(value = "是否展示")
    private Integer showStatus;

    @ApiModelProperty(value = "品牌logo")
    @NotBlank(message = "品牌logo不能为空")
    private String logo;

    @ApiModelProperty(value = "专区大图")
    @NotBlank(message = "专区大图不能为空")
    private String bigPic;

    @ApiModelProperty(value = "品牌故事")
    @NotBlank(message = "品牌故事不能为空")
    private String brandStory;

}
