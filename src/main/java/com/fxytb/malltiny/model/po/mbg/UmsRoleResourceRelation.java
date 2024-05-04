package com.fxytb.malltiny.model.po.mbg;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class UmsRoleResourceRelation implements Serializable {
    private Long id;

    @ApiModelProperty(value="角色ID")
    private Long roleId;

    @ApiModelProperty(value="资源ID")
    private Long resourceId;

    private static final long serialVersionUID = 1L;
}