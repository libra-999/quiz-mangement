package org.example.tol.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class QuestionDetailRS {

    private String id;
    private String question;
    private int answer;
    private List<String> options;
}
