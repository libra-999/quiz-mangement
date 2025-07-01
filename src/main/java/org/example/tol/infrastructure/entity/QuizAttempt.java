package org.example.tol.infrastructure.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.Map;

@Data
@Document
public class QuizAttempt {

    @Id
    private String id;

    @Field(name = "user_id")
    private String userId;

    @Field(name = "quiz_id")
    private String quizId;

    @Field(name = "answers")
    private Map<String, Integer> answers;

    @Field(name = "score")
    private int score;

    @Field(name = "submitted_at")
    private Date submittedAt;

}
