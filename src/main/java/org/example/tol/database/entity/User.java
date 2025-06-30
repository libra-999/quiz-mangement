package org.example.tol.database.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collation = "users")
public class User {

    @Id
    private String id;
    private String username;
    private String name;
    private String dob;
    private int gender;
    private boolean isActive;
    private Date createTime;
    private Date updateTime;


}
