package org.example.tol.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.tol.controller.mapper.AttemptMapper;
import org.example.tol.controller.request.AttemptRQ;
import org.example.tol.controller.response.AttemptDetailRS;
import org.example.tol.controller.response.AttemptRS;
import org.example.tol.domain.service.AttemptService;
import org.example.tol.infrastructure.entity.QuizAttempt;
import org.example.tol.share.entity.HttpBodyPagingResponse;
import org.example.tol.share.entity.HttpBodyResponse;
import org.example.tol.share.entity.PaginationQuery;
import org.example.tol.share.entity.Paging;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.tol.share.controller.ControllerHandler.*;

@RestController
@RequestMapping("/v1/api/attempt")
@Tag(name = "Admin - Quiz Attempt")
@RequiredArgsConstructor
public class AttemptController {

    private final AttemptMapper mapper;
    private final AttemptService service;

    @Operation(summary = "list")
    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<HttpBodyResponse<List<AttemptRS>>> list(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size) {
        Paging<QuizAttempt> paging = service.list(PaginationQuery.of(page, size, keyword));
        return responsePaging(
            paging.getItems().stream().map(mapper::from).toList(),
            HttpBodyPagingResponse.of(
                paging.getPage(),
                paging.getSize(),
                paging.getTotal(),
                paging.getTotalPages()
            )
        );

    }

    @Operation(summary = "view")
    @GetMapping("/{attemptId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<HttpBodyResponse<AttemptDetailRS>> view(@PathVariable String attemptId){
        return responseSucceed(mapper.to(service.view(attemptId)));
    }

    @Operation(summary = "create")
    @PostMapping("/{quizId}/submitted")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<HttpBodyResponse<AttemptRS>> create(@RequestBody @Validated AttemptRQ request, @PathVariable String quizId){
        return responseCreated(mapper.from(service.create(request, quizId)));
    }

}
