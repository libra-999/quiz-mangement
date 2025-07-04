package org.example.tol.controller.request;

import jakarta.validation.constraints.Min;
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
public class QuizRQ {

    @NotNull(message = "TITLE_IS_REQUIRED")
    @NotBlank(message = "TITLE ")
    private String title;

    @NotNull(message = "DURATION_IS_REQUIRED")
    @Min(5)
    private long duration;

    @NotNull(message = "DESCRIPTION_IS_REQUIRED")
    private String description;

    @NotNull(message = "Questions_must_be_required")
    private List<QuestionRQ> questions;
}
