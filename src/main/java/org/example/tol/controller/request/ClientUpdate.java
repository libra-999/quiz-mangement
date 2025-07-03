package org.example.tol.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientUpdate {

    private String name;

    @Pattern(message = "Incorrect date format", regexp = "^[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{4}$")
    private String dob;

    @Min(0)
    private int gender;

}
