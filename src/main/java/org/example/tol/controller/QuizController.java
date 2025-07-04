package org.example.tol.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.tol.controller.mapper.QuizMapper;
import org.example.tol.controller.request.QuestionRQ;
import org.example.tol.controller.request.QuizRQ;
import org.example.tol.controller.request.QuizUpdate;
import org.example.tol.controller.response.QuizDetailRS;
import org.example.tol.controller.response.QuizRS;
import org.example.tol.domain.service.QuizService;
import org.example.tol.infrastructure.entity.Quiz;
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
@Tag(name = "Admin - Quiz")
@RequestMapping("/v1/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService service;
    private final QuizMapper mapper;


    @Operation(summary = "list")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<HttpBodyResponse<List<QuizRS>>> list(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size
    ) {
        Paging<Quiz> paging = service.list(PaginationQuery.of(page, size, keyword), null);
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
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{quizId}")
    public ResponseEntity<HttpBodyResponse<QuizDetailRS>> view(@PathVariable String quizId)
    {
        return responseSucceed(mapper.to(service.view(quizId)));
    }


    @Operation(summary = "create")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<HttpBodyResponse<QuizRS>> create(@RequestBody @Validated QuizRQ request){
        return responseCreated(mapper.from(service.create(request)));
    }

    @Operation(summary = "update")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{quizId}")
    public ResponseEntity<HttpBodyResponse<QuizRS>> update(@PathVariable String quizId,
                                                           @RequestBody @Validated QuizUpdate request){
        return responseSucceed(mapper.from(service.update(request,quizId)));
    }

    @Operation(summary = "add questions into quiz")
    @PutMapping("/{quizId}/questions")
    @SecurityRequirement(name = "bearerAuth")
    public  ResponseEntity<HttpBodyResponse<QuizDetailRS>> addQuestions(@PathVariable String quizId, @RequestBody List<QuestionRQ> questions){
        return responseSucceed(mapper.to(service.addQuesList(questions, quizId)));
    }

    @Operation(summary = "delete")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{quizId}")
    public ResponseEntity<?> delete(@PathVariable String quizId){
        service.delete(quizId);
        return  responseDeleted();
    }

    @Operation(summary = "delete all questions")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{quizId}/questions")
    public ResponseEntity<?> deleteQuestions(@PathVariable String quizId){
        service.deletedAllQues(quizId);
        return responseDeleted();
    }
}
