package com.fxytb.malltiny.model.po.elasticsearch;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsPmsProductAttributeValue implements Serializable {
    private Long id;

    private Long productId;

    private Long productAttributeId;

    @ApiModelProperty(value = "手动添加规格或参数的值，参数单值，规格有多个时以逗号隔开")
    @Field(type = FieldType.Keyword)
    private String value;

    private Integer type;


    @Field(type = FieldType.Keyword)
    private String name;

    private static final long serialVersionUID = 1L;
}
