package org.example.tol.database.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Data
@Document(collation = "quiz_attempts")
public class QuizAttempt {

    @Id
    private String id;
    private String userId;
    private String quizId;
    private Map<String, Integer> answers;
    private int score;
    private Date submittedAt;

}
