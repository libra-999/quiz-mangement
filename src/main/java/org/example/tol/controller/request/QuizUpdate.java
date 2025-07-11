package org.example.tol.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class QuizUpdate {

    private String text;
    private String description;
    private int duration;

}
