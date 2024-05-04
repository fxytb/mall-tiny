package com.fxytb.malltiny.common.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "分页查询参数", description = "分页查询参数")
public class PageQuery<T> {

    @ApiModelProperty(value = "当前页码")
    @NotNull(message = "当前页码不能为空")
    private Integer pageNum;

    @ApiModelProperty(value = "每页显示条数")
    @NotNull(message = "每页显示条数不能为空")
    private Integer pageSize;

    @ApiModelProperty(value = "其他参数")
    private T data;

}
