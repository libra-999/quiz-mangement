package org.example.tol.database.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collation = "questions")
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
}
