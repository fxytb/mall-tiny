package com.fxytb.malltiny.model.po.mbg;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UmsRoleMenuRelation implements Serializable {
    private Long id;

    @ApiModelProperty(value="角色ID")
    private Long roleId;

    @ApiModelProperty(value="菜单ID")
    private Long menuId;

    private static final long serialVersionUID = 1L;
}