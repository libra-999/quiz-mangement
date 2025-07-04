package org.example.tol.domain.application;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tol.controller.request.AttemptRQ;
import org.example.tol.controller.response.AttemptRS;
import org.example.tol.domain.exception.AttemptException;
import org.example.tol.domain.exception.QuizException;
import org.example.tol.domain.exception.UserException;
import org.example.tol.domain.service.AttemptService;
import org.example.tol.infrastructure.entity.Question;
import org.example.tol.infrastructure.entity.Quiz;
import org.example.tol.infrastructure.entity.QuizAttempt;
import org.example.tol.infrastructure.entity.User;
import org.example.tol.infrastructure.repository.QuizAttemptRepository;
import org.example.tol.infrastructure.repository.UserRepository;
import org.example.tol.share.entity.PageNumber;
import org.example.tol.share.entity.PaginationQuery;
import org.example.tol.share.entity.Paging;
import org.example.tol.share.utility.TemplateFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttemptFacade implements AttemptService {

    private final QuizAttemptRepository attemptRepository;
    private final UserRepository userRepository;
    private final TemplateFactory templateFactory;


    @Override
    public Paging<QuizAttempt> list(PaginationQuery query) {
        Page<QuizAttempt> page = templateFactory.builder(QuizAttempt.class)
            .withKeyword(query.getKeyword(), "user_id")
            .deletedIsNull("deleteTime")
            .withSort(Sort.by(Sort.Direction.DESC, "_id"))
            .execute(PageRequest.of(PageNumber.in(query.getPage()), query.getSize()));


        return Paging.<QuizAttempt>builder()
            .items(page.stream().toList())
            .page(PageNumber.out(page.getNumber()))
            .size(page.getSize())
            .total(page.getTotalPages())
            .totalPages(page.getTotalPages()).build();
    }

    @Override
    public QuizAttempt view(String attemptId) {
        return templateFactory.builder(QuizAttempt.class).isNull(attemptId, "deleteTime")
            .findOne().orElseThrow(AttemptException::notFound);
    }

    @Override
    @Transactional
    public QuizAttempt create(AttemptRQ request, String quizId) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(UserException::notFound);
        Quiz quiz = templateFactory.builder(Quiz.class).isNull(quizId, "deleteTime")
            .findOne().orElseThrow(QuizException::notFound);

        List<Question> questions = templateFactory.builder(Question.class)
            .withFilters("quiz_id", "is", quiz.getId())
            .deletedIsNull("deleteTime")
            .findAll();

        int score = 0;
        Map<String, Integer> answers = request.getAnswer();
        QuizAttempt attempt = new QuizAttempt();
        attempt.setUserId(user.getId());
        attempt.setQuizId(quiz.getId());
        attempt.setCreateTime(new Date());
        attempt.setUpdateTime(null);
        attempt.setDeleteTime(null);

        for (Question newQuestion : questions) {
            Integer userAnw = answers.get(newQuestion.getId());
            if (Objects.nonNull(userAnw) && userAnw == newQuestion.getCorrectAnswer()) {
                score++;
            }
        }
        attempt.setAnswers(answers);
        attempt.setSubmittedAt(new Date());
        attempt.setScore(score);


        AttemptRS attemptRS = new AttemptRS();
        attemptRS.setUsername(user.getUsername());
        attemptRS.setTitle(quiz.getTitle());
        return attemptRepository.save(attempt);
    }
}
