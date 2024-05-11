package com.fxytb.malltiny.model.po.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "blank")
@Setting(shards = 1, replicas = 0)
public class EsBlank {

    @Field(type = FieldType.Integer)
    private Integer account_number;
    @Field(type = FieldType.Double)
    private BigDecimal balance;
    @Field(type = FieldType.Keyword)
    private String firstname;
    @Field(type = FieldType.Keyword)
    private String lastname;
    private Integer age;
    private String gender;
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String address;
    private String employer;
    private String email;
    private String city;
    private String state;

}
