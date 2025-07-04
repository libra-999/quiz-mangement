package org.example.tol.domain.service;

import org.example.tol.controller.request.QuestionRQ;
import org.example.tol.controller.request.QuizRQ;
import org.example.tol.controller.request.QuizUpdate;
import org.example.tol.infrastructure.entity.Quiz;
import org.example.tol.share.entity.Filter;
import org.example.tol.share.entity.PaginationQuery;
import org.example.tol.share.entity.Paging;

import java.util.List;

public interface QuizService {

    Paging<Quiz> list(PaginationQuery query, Filter filter);

    Quiz view(String quizId);

    Quiz create(QuizRQ request);

    Quiz update(QuizUpdate request, String quizId);

    Quiz addQuesList(List<QuestionRQ> request, String quizId);

    void delete(String quizId);

    void deletedAllQues(String quizId);
}
