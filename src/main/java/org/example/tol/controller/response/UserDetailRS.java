package org.example.tol.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailRS {

    private String id;
    private String name;
    private String username;
    private String gender;
    private String dob;
    private String createTime;

}
