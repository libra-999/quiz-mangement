package org.example.tol.domain.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tol.controller.request.QuestionUpdate;
import org.example.tol.domain.exception.QuestionException;
import org.example.tol.domain.service.QuestionService;
import org.example.tol.infrastructure.entity.Question;
import org.example.tol.infrastructure.repository.QuestionRepository;
import org.example.tol.share.entity.PageNumber;
import org.example.tol.share.entity.PaginationQuery;
import org.example.tol.share.entity.Paging;
import org.example.tol.share.utility.TemplateFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionFacade implements QuestionService {

    private final QuestionRepository quesRepository;
    private final TemplateFactory templateFactory;

    @Override
    public Paging<Question> list(PaginationQuery query) {

        Page<Question> page = templateFactory.builder(Question.class)
            .deletedIsNull("deleteTime")
            .withKeyword(query.getKeyword(), "question")
            .withSort(Sort.by(Sort.Direction.DESC, "id"))
            .execute(PageRequest.of(PageNumber.in(query.getPage()), query.getSize()));

        return Paging.<Question>builder()
            .items(page.stream().toList())
            .page(PageNumber.out(page.getNumber()))
            .size(page.getSize())
            .total(page.getTotalElements())
            .totalPages(page.getTotalPages()).build();
    }

    @Override
    public Question view(String quesId) {
        return templateFactory.builder(Question.class).isNull(quesId,"deleteTime").findOne().orElseThrow(QuestionException::notFound);
    }

    @Override
    public Question update(QuestionUpdate request, String quesId) {
        Question entity = templateFactory.builder(Question.class).isNull(quesId,"deleteTime").findOne().orElseThrow(QuestionException::notFound);

        entity.setQuestion(Objects.nonNull(request.getQuestion()) ? request.getQuestion() : entity.getQuestion());
        entity.setCorrectAnswer(request.getCorrectAnswer());
        entity.setOptions(Objects.nonNull(request.getQuestion()) ? request.getOptions() : entity.getOptions());
        entity.setUpdateTime(new Date());
        return quesRepository.save(entity);
    }

    @Override
    public void delete(String quesId) {
        Question entity = templateFactory.builder(Question.class).isNull(quesId,"deleteTime").findOne().orElseThrow(QuestionException::notFound);
        entity.setDeleteTime(new Date());
        quesRepository.delete(entity);
    }
}
