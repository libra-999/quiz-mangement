package org.example.tol.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UserRS {

    private String id;
    private String username;
    private String dob;
    private String gender;
    private boolean isActive;

}
