package org.example.tol.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.tol.domain.service.LeaderBoardService;
import org.example.tol.share.entity.HttpBodyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.tol.share.controller.ControllerHandler.responseSucceed;

@RestController
@RequestMapping("/v1/api/leaderboards")
@RequiredArgsConstructor
@Tag(name = "Admin - Leaderboard")
public class LeaderBoardController {

    private final LeaderBoardService leaderBoardService;


    @Operation(summary = "Top 10 of students")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{quizId}")
    public ResponseEntity<HttpBodyResponse<Object>> view(@PathVariable String quizId,
                                                         @RequestParam(required = false, defaultValue = "10") int limit) {
        return responseSucceed(leaderBoardService.viewLeaderboard(quizId, limit));
    }

}
