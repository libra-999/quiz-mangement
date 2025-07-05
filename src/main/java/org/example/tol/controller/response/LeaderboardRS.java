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
public class LeaderboardRS {

    private String title;
    private long duration;
    private List<Participate> participates;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Participate {

        private String username;
        private int score;
        private List<String> questions;
    }

}
