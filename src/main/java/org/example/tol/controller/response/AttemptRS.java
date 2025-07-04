package org.example.tol.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
public class AttemptRS {

    private String id;
    private String username;
    private String title;
    private Map<String, Integer> answers;
}
