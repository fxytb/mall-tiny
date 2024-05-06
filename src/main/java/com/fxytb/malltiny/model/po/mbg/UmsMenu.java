package com.fxytb.malltiny.model.po.mbg;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UmsMenu implements Serializable {
    private Long id;

    @ApiModelProperty(value="父级ID")
    private Long parentId;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="菜单名称")
    private String title;

    @ApiModelProperty(value="菜单级数")
    private Integer level;

    @ApiModelProperty(value="菜单排序")
    private Integer sort;

    @ApiModelProperty(value="前端名称")
    private String name;

    @ApiModelProperty(value="前端图标")
    private String icon;

    @ApiModelProperty(value="前端隐藏")
    private Integer hidden;

    private static final long serialVersionUID = 1L;
}