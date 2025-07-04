package org.example.tol.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizRS {

    private String id;
    private String title;
    private int duration; // store as mins
    private List<subQuesRS> questions;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class subQuesRS{
        private String question;
        private List<String> options;
    }
}


