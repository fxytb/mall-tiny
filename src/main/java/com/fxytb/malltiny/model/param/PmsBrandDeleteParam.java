package com.fxytb.malltiny.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "品牌删除参数",description = "品牌删除参数")
public class PmsBrandDeleteParam {

    @ApiModelProperty(value = "品牌ID")
    @NotNull(message = "品牌ID不能为空")
    private Long id;

}
