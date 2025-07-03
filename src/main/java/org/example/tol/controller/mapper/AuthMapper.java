package org.example.tol.controller.mapper;

import org.example.tol.controller.response.RegisterRS;
import org.example.tol.infrastructure.entity.User;
import org.example.tol.share.constant.Gender;
import org.mapstruct.*;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface AuthMapper {

    @Mapping(target = "gender", expression = "java(convertGender(entity))")
    RegisterRS from(User entity);

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
