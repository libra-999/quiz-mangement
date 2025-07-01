package org.example.tol.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class RegisterRS {

    private String username;
    private String name;
    private String dob;
    private String gender;
    private boolean isActive;
    private Date createTime;
}
