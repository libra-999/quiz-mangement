package org.example.tol.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.tol.controller.mapper.UserMapper;
import org.example.tol.controller.request.Client;
import org.example.tol.controller.request.ClientUpdate;
import org.example.tol.controller.response.UserDetailRS;
import org.example.tol.controller.response.UserRS;
import org.example.tol.domain.service.UserService;
import org.example.tol.infrastructure.entity.User;
import org.example.tol.share.entity.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.tol.share.controller.ControllerHandler.*;

@RestController
@RequestMapping("/v1/api/users")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserMapper mapper;
    private final UserService service;

    @Operation(summary = "list")
    @SecurityRequirement(name = "bearerAuth")
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

    @Operation(summary = "view")
    @GetMapping("/{userId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<HttpBodyResponse<UserDetailRS>> view(@PathVariable String userId) {
        return responseSucceed(mapper.to(service.view(userId)));
    }

    @Operation(summary = "create")
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<HttpBodyResponse<UserRS>> create(@RequestBody @Validated Client request) {
        return responseCreated(mapper.from(service.create(request)));
    }


    @Operation(summary = "update")
    @PutMapping("/{userId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<HttpBodyResponse<UserRS>> update(@RequestBody @Validated ClientUpdate request, @PathVariable String userId) {
        return responseSucceed(mapper.from(service.update(userId, request)));
    }

    @Operation(summary = "delete")
    @DeleteMapping("/{userId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> delete(@PathVariable String userId){
        service.delete(userId);
        return responseDeleted();
    }

    @Operation(summary = "deleteAll")
    @DeleteMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteAll(){
        service.deleteAll();
        return  responseDeleted();
    }

//    @Operation(summary = "update status")
//    @PutMapping("/{userId}")
//    public ResponseEntity<?> updateStatus(@PathVariable String userId){
//        service.isActive(userId);
//        return responseSucceed();
//    }
}
