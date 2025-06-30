package org.example.tol.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@Tag (name = "Login", description = "Login to get token ")
public class TestController {

    @Operation(tags = "Login")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "success",  content = {
            @Content(mediaType = "application/json") })
    })
    @GetMapping("/")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("login success");
    }

}
