package org.example.tol.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Log {

    @NotNull(message = "USERNAME_IS_REQUIRED")
    @NotBlank(message = "USERNAME_IS_REQUIRED")
    private String username;

    @NotNull(message = "PASSWORD_IS_REQUIRED")
    @NotBlank(message = "USERNAME_IS_REQUIRED")
    private String password;
}
