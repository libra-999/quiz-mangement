package org.example.tol.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttemptDetailRS {

    private String id;
    private String username;
    private QuizRS quiz;
    private int score;
    private Map<String, Integer> answers;

}
