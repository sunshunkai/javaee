package com.ssk.jpa.mode;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author ssk
 * @date 2020/12/31
 */
@Data
@Document(indexName = "houseindex",type = "house")
public class HouseIndexTemplate {
    @Id
    private Long id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;

    @Field(type=FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Integer)
    private int price;
}
