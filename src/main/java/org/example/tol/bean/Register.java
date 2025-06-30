package org.example.tol.bean;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Register {

    @NotNull(message = "USERNAME_IS_REQUIRED")
    private String username;
    private String name;

    @NotNull(message = "PASSWORD_IS_REQUIRED")
    private String password;

    @NotNull(message = "DOB_IS_REQUIRED")
    private String dob;

    @NotNull(message = "GENDER_IS_REQUIRED")
    private int gender;
}
