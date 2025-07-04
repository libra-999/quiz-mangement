package org.example.tol.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class AttemptRQ {

    @NotNull(message = "UserId must be required")
    private String userId;

    @NotNull(message = "Answer must be required")
    private Map<String, Integer> answer;
}
