package org.example.tol.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class Client {

    @NotBlank(message = "USERNAME_IS_REQUIRED")
    @NotNull(message = "USERNAME_IS_REQUIRED")
    private String username;

    @NotNull(message = "PASSWORD_IS_REQUIRED")
    @NotBlank(message = "PASSWORD_IS_REQUIRED")
    @Pattern(message = "Password must be at least 8 character, include symbol and number", regexp = "^(?=.*?[A-Z]).{8,15}$")
    private String password;
    private String name;

    @NotNull(message = "DOB_IS_REQUIRED")
    @NotBlank(message = "DOB_IS_REQUIRED")
    @Pattern(message = "Incorrect date format", regexp = "^[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{4}$")
    private String dob;

    @NotNull(message = "GENDER_IS_REQUIRED")
    @Min(0)
    private int gender;
}
