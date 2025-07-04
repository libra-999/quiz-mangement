package org.example.tol.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.tol.controller.mapper.QuizMapper;
import org.example.tol.controller.request.QuestionUpdate;
import org.example.tol.controller.response.QuestionDetailRS;
import org.example.tol.controller.response.QuestionRS;
import org.example.tol.domain.service.QuestionService;
import org.example.tol.infrastructure.entity.Question;
import org.example.tol.share.entity.HttpBodyPagingResponse;
import org.example.tol.share.entity.HttpBodyResponse;
import org.example.tol.share.entity.PaginationQuery;
import org.example.tol.share.entity.Paging;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.tol.share.controller.ControllerHandler.*;

@Tag(name = "Admin - Question")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/questions")
public class QuestionController {

    private final QuizMapper mapper;
    private final QuestionService service;

    @Operation(summary = "list")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<HttpBodyResponse<List<QuestionRS>>> list(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size
    ) {
        Paging<Question> paging = service.list(PaginationQuery.of(page, size, keyword));
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
    @GetMapping("/{quesId}")
    public ResponseEntity<HttpBodyResponse<QuestionDetailRS>> view(@PathVariable String quesId){
        return responseSucceed(mapper.to(service.view(quesId)));
    }


    @Operation(summary = "update")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{quesId}")
    public ResponseEntity<HttpBodyResponse<QuestionDetailRS>> update(@PathVariable String quesId, @RequestBody QuestionUpdate request) {
        return responseSucceed(mapper.to(service.update(request, quesId)));
    }


    @Operation(summary = "delete")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{quesId}")
    public ResponseEntity<?> delete(@PathVariable String  quesId) {
        service.delete(quesId);
        return responseDeleted();
    }
}
