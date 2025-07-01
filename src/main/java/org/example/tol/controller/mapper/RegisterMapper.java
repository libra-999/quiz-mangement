package org.example.tol.controller.mapper;

import org.example.tol.controller.response.RegisterRS;
import org.example.tol.infrastructure.entity.User;
import org.mapstruct.*;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface RegisterMapper {

    RegisterRS from(User entity);
}
