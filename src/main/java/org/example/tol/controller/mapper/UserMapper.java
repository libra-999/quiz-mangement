package org.example.tol.controller.mapper;

import org.example.tol.controller.request.Client;
import org.example.tol.controller.response.UserRS;
import org.example.tol.infrastructure.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface UserMapper {

    UserRS from(User entity);

    User from(Client request);

}
