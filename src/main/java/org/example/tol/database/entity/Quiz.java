package org.example.tol.database.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collation = "quizzes")
@Data
public class Quiz {

    @Id
    private String id;
    private String title;
    private String description;
    private List<Question> questions;
}
