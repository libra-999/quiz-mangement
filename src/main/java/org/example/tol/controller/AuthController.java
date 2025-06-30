package org.example.tol.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.tol.bean.Log;
import org.example.tol.bean.Register;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("v1/api/auth")
@RestController
@Tag (name = "Auth")
public class AuthController {

    @Operation(summary = "login")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "SUCCESS",  content = {
            @Content(mediaType = "application/json")
        }),
        @ApiResponse(responseCode = "401", content = {
            @Content(mediaType = "application/json")
        })
    })
    @RouterOperation(params = "login")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Validated Log request) {
        return ResponseEntity.ok("login success");
    }


    @Operation(summary = "register")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "CREATED", content = {
            @Content(mediaType = "application/json")
        })
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Validated Register request) {
        return ResponseEntity.ok("register success");
    }






}
