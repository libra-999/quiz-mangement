package org.example.tol.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.tol.controller.mapper.AuthMapper;
import org.example.tol.controller.request.Log;
import org.example.tol.controller.request.RegisterRQ;
import org.example.tol.controller.response.LoginRS;
import org.example.tol.controller.response.RegisterRS;
import org.example.tol.domain.service.AuthService;
import org.example.tol.share.entity.HttpBodyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.example.tol.share.controller.ControllerHandler.*;

@Tag (name = "Admin - Auth")
@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthMapper mapper;

    @Operation(summary = "login")
    @PostMapping("/login")
    public ResponseEntity<HttpBodyResponse<LoginRS>> login(@RequestBody @Validated Log request)
        throws JsonProcessingException {
        Map<String, Object> map = authService.login(request);
        LoginRS response = new LoginRS();

        response.setUser(fromJsonString((String) map.get("user"), RegisterRS.class));
        response.setToken(map.get("token").toString());
        return responseSucceed(response);
    }

    @Operation(summary = "register")
    @PostMapping("/register")
    public ResponseEntity<HttpBodyResponse<RegisterRS>> register(@RequestBody @Validated RegisterRQ request) {
        return responseCreated(mapper.from(authService.register(request)));
    }


}
