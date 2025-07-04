package org.example.tol.controller.mapper;

import org.example.tol.controller.response.AttemptDetailRS;
import org.example.tol.controller.response.AttemptRS;
import org.example.tol.infrastructure.entity.QuizAttempt;
import org.mapstruct.*;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface AttemptMapper {

    AttemptRS from(QuizAttempt quizAttempt);

    AttemptDetailRS to(QuizAttempt quizAttempt);



}
