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
public class Question {

    @Id
    private String id;

    @Field(name = "text")
    private String question;

    @Field(name = "correct_answer")
    private int correctAnswer;

    @Field(name = "options")
    private List<String> options;

    @CreatedDate
    @Field(name = "createTime")
    public Date createTime;

    @LastModifiedDate
    @Field(name = "updateTime")
    public Date updateTime;

    @Field(name = "deleteTime")
    public Date deleteTime;

}
