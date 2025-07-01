package org.example.tol.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class Question {

    private String id;
    private String text;
    private int correctAnswer;
    private List<String> options;
}
