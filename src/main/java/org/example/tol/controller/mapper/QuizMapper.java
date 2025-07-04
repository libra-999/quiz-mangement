package org.example.tol.controller.mapper;

import org.example.tol.controller.request.QuizRQ;
import org.example.tol.controller.response.QuestionDetailRS;
import org.example.tol.controller.response.QuestionRS;
import org.example.tol.controller.response.QuizDetailRS;
import org.example.tol.controller.response.QuizRS;
import org.example.tol.infrastructure.entity.Question;
import org.example.tol.infrastructure.entity.Quiz;
import org.mapstruct.*;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface QuizMapper {

    QuizRS from(Quiz entity);

    QuizDetailRS to(Quiz entity);

    QuestionRS from(Question entity);

    @Mapping(target = "answer", source = "correctAnswer")
    QuestionDetailRS to(Question entity);

    @Mapping(target = "questions", source = "questions")
    Quiz from (QuizRQ request);

}
