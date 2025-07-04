package org.example.tol.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRQ {

    @NotNull(message = "USERNAME_IS_REQUIRED")
    @NotBlank(message = "USERNAME_IS_REQUIRED")
    private String username;
    private String name;

    @NotNull(message = "PASSWORD_IS_REQUIRED")
    @NotBlank(message = "PASSWORD_IS_REQUIRED")
    @Pattern(message = "Password must be at least 8 character, include symbol and number", regexp = "^(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[#?!@$%^&*<>,.:;(){}]).{8,15}$")
    private String password;

    @NotNull(message = "DOB_IS_REQUIRED")
    @NotBlank(message = "DOB_IS_REQUIRED")
    @Pattern(message = "Incorrect date format", regexp = "^[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{4}$")
    private String dob;

    @NotNull(message = "GENDER_IS_REQUIRED")
    private int gender;
}
