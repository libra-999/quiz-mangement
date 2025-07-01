package org.example.tol.infrastructure.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
}
