package org.example.tol.infrastructure.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Document
@Data
public class Quiz {

    @Id
    private String id;

    @Field(name = "title")
    private String title;

    @Field(name = "description")
    private String description;

    @Field(name = "duration")
    private long duration;

    @Field(name = "questions")
    private List<Question> questions;

    @CreatedDate
    @Field(name = "createTime")
    public Date createTime;

    @LastModifiedDate
    @Field(name = "updateTime")
    public Date updateTime;

    @Field(name = "deleteTime")
    public Date deleteTime;
}
