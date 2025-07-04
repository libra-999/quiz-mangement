package org.example.tol.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDetailRS {

    private String title;
    private String duration;
    private String description;
    private List<QuestionDetailRS> questions;
}
