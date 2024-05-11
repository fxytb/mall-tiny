package com.fxytb.malltiny.model.po.elasticsearch;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "brand")
@Setting(shards = 1, replicas = 0)
public class EsPmsBrand implements Serializable {
    @Id
    private Long id;

    @Field(maxShingleSize = 20, analyzer = "ik_max_word", type = FieldType.Text)
    @HighlightParameters(tagsSchema = "<span></span>")
    private String name;

    @ApiModelProperty(value = "首字母")
    @Field(type = FieldType.Keyword)
    private String firstLetter;

    @Field(type = FieldType.Integer)
    private Integer sort;

    @ApiModelProperty(value = "是否为品牌制造商：0->不是；1->是")
    private Integer factoryStatus;

    @Field(type = FieldType.Integer)
    private Integer showStatus;

    @ApiModelProperty(value = "产品数量")
    @Field(type = FieldType.Integer)
    private Integer productCount;

    @ApiModelProperty(value = "产品评论数量")
    @Field(type = FieldType.Integer)
    private Integer productCommentCount;

    @ApiModelProperty(value = "品牌logo")
    @Field(type = FieldType.Text)
    private String logo;

    @ApiModelProperty(value = "专区大图")
    @Field(type = FieldType.Text)
    private String bigPic;

    @ApiModelProperty(value = "品牌故事")
    @Field(type = FieldType.Text)
    private String brandStory;

    private static final long serialVersionUID = 1L;
}