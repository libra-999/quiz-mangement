package org.example.tol.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionUpdate {

    private String question;
    private int correctAnswer;
    private List<String> options;

}
