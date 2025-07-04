package org.example.tol.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class QuestionRQ {

    @NotBlank(message = "QUESTION_IS_REQUIRED")
    @NotNull
    private String question;

    @NotNull(message = "Answer_must_be_required")
    private int correctAnswer;

    @NotBlank(message = "OPTIONS_MUST_BE_REQUIRED")
    @NotNull
    private List<String> options;
}
