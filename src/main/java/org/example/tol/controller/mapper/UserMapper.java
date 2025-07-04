package org.example.tol.controller.mapper;

import org.example.tol.controller.request.Client;
import org.example.tol.controller.response.UserDetailRS;
import org.example.tol.controller.response.UserRS;
import org.example.tol.infrastructure.entity.User;
import org.example.tol.share.constant.Gender;
import org.mapstruct.*;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface UserMapper {

    @Mapping(target = "gender", expression = "java(convertGender(entity))")
    UserDetailRS to(User entity);

    @Mapping(target = "gender", expression = "java(convertGender(entity))")
    UserRS from(User entity);

    User from(Client request);

    default String convertGender(User entity) {
        if (entity.getGender() == Gender.MALE.getType()) {
            return "Male";
        } else if (entity.getGender() == Gender.FEMALE.getType()) {
            return "Female";
        } else {
            return "Unknown";
        }
    }

}
