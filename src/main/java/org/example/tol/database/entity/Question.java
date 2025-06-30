package org.example.tol.database.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collation = "questions")
@Data
public class Question {

    @Id
    private String id;
    private String question;
    private int correctAnswer;
    private List<String> options;
}
