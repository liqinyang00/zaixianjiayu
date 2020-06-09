package com.ed.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;


@Data
@Document(indexName = "1908_user_index", type = "1908_user_type")
public class CourseEntity implements Serializable {
    @Id
    private Integer courseid;
    @Field(name = "coursetitle" ,type = FieldType.Text, analyzer = "ik_max_word", store = true)
    private String coursetitle;
    @Field(name="coursetype", type = FieldType.Integer, index = false)
    private Integer coursetype;
    @Field(name="coursenumber", type = FieldType.Integer, index = false)
    private Integer coursenumber;
    @Field(name="courseimg", type = FieldType.Keyword, index = false)
    private String courseimg;
    @Field(name="authername", type = FieldType.Keyword, index = false)
    private String authername;
    @Field(name="autherimg", type = FieldType.Keyword, index = false)
    private String autherimg;
    @Field(name="courseprice", type = FieldType.Double, index = false)
    private Double courseprice;
    @Field(name="coursecontext", type = FieldType.Keyword, index = false)
    private String coursecontext;
    @Field(name="name", type = FieldType.Keyword, index = false)
    private String name;

}
