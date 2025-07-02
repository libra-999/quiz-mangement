package org.example.tol.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.tol.controller.mapper.UserMapper;
import org.example.tol.controller.response.UserRS;
import org.example.tol.domain.service.UserService;
import org.example.tol.infrastructure.entity.User;
import org.example.tol.util.entity.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.example.tol.util.controller.ControllerHandler.responsePaging;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserMapper mapper;
    private final UserService service;

    @Operation(tags = "User")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "SUCCESS", content = {
            @Content(mediaType = "application/json")})
    })
    @GetMapping
    public ResponseEntity<HttpBodyResponse<List<UserRS>>> user(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) boolean status
    ) {
        Paging<User> paging = service.list(PaginationQuery.of(page, size, keyword), Filter.of(status, null, null, null));
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
}
