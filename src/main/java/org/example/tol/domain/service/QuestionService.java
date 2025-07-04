package org.example.tol.domain.service;

import org.example.tol.controller.request.QuestionUpdate;
import org.example.tol.infrastructure.entity.Question;
import org.example.tol.share.entity.PaginationQuery;
import org.example.tol.share.entity.Paging;

public interface QuestionService {

    Paging<Question> list(PaginationQuery query);

    Question view(String quesId);

    Question update(QuestionUpdate request, String quesId);

    void delete(String quesId);
}
