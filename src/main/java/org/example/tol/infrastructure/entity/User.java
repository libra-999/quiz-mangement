package org.example.tol.infrastructure.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document
public class User {

    @Id
    private String id;

    @Field(name = "username")
    private String username;

    @Field(name = "password")
    private String password;

    @Field(name = "name")
    private String name;

    @Field(name = "dob")
    private String dob;

    @Field(name = "gender")
    private int gender;

    @Field(name = "is_active")
    private boolean isActive;

    @Field(name = "createTime")
    @CreatedDate
    private Date createTime;

    @Field(name = "updateTime")
    @LastModifiedDate
    private Date updateTime;

}
