package org.example.tol.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
@Tag(name = "User" , description = "Data of User")
public class UserController {

    @Operation(tags = "User")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "success",  content = {
            @Content(mediaType = "application/json") })
    })
    @GetMapping
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("user 1");
    }
}
