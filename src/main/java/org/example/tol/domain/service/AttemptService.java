package org.example.tol.domain.service;


import org.example.tol.controller.request.AttemptRQ;
import org.example.tol.infrastructure.entity.QuizAttempt;
import org.example.tol.share.entity.PaginationQuery;
import org.example.tol.share.entity.Paging;

public interface AttemptService {

    Paging<QuizAttempt> list(PaginationQuery query);

    QuizAttempt view(String attemptId);

    QuizAttempt create(AttemptRQ request, String quizId);
}
