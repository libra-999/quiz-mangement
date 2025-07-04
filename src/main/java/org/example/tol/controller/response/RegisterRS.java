package org.example.tol.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterRS {

    private String username;
    private String name;
    private String dob;
    private String gender;
    private boolean isActive;
    private String createTime;
}
