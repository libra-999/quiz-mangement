package org.example.tol.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class User {

    private String id;
    private String username;
    private String name;
    private String dob;
    private int gender;
    private boolean isActive;
    private Date createTime;
    private Date updateTime;

}
