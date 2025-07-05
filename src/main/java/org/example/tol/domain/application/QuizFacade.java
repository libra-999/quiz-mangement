package org.example.tol.domain.application;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.tol.controller.request.QuestionRQ;
import org.example.tol.controller.request.QuizRQ;
import org.example.tol.controller.request.QuizUpdate;
import org.example.tol.domain.exception.QuizException;
import org.example.tol.domain.service.QuizService;
import org.example.tol.infrastructure.entity.Question;
import org.example.tol.infrastructure.entity.Quiz;
import org.example.tol.infrastructure.redis.RedisService;
import org.example.tol.infrastructure.redis.config.RedisKey;
import org.example.tol.infrastructure.repository.QuestionRepository;
import org.example.tol.infrastructure.repository.QuizRepository;
import org.example.tol.share.entity.Filter;
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
import java.util.concurrent.TimeUnit;

import static org.example.tol.share.controller.ControllerHandler.fromJsonString;
import static org.example.tol.share.controller.ControllerHandler.toJsonString;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizFacade implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final TemplateFactory templateFactory;
    private final RedisService redisService;

    @Override
    public Paging<Quiz> list(PaginationQuery query, Filter filter) {
        Page<Quiz> page = templateFactory.builder(Quiz.class)
            .withKeyword(query.getKeyword(), "title")
            .deletedIsNull("deleteTime")
            .withSort(Sort.by(Sort.Direction.DESC, "id"))
            .execute(PageRequest.of(PageNumber.in(query.getPage()), query.getSize()));

        return Paging.<Quiz>builder()
            .items(page.stream().toList())
            .size(page.getSize())
            .page(PageNumber.out(page.getNumber()))
            .total(page.getTotalPages())
            .totalPages(page.getTotalPages())
            .build();
    }

    @Override
    @SneakyThrows
    @Transactional(readOnly = true)
    public Quiz view(String quizId) {
        Quiz quiz = templateFactory.builder(Quiz.class).isNull(quizId, "deleteTime").findOne().orElseThrow(QuizException::notFound);

        if (redisService.getValue(RedisKey.VALUE_QUIZ) != null){
            return fromJsonString(redisService.getValue(RedisKey.VALUE_QUIZ), Quiz.class);
        }else {
            redisService.getValue(RedisKey.VALUE_QUIZ,toJsonString(quiz), 3, TimeUnit.MINUTES);
            return quiz;
        }
    }

    @Override
    @Transactional
    public Quiz create(QuizRQ request) {
        Quiz quiz = new Quiz();
        List<Question> quizList = new ArrayList<>();

        quiz.setTitle(request.getTitle());
        quiz.setDuration(request.getDuration() <= 5 ? 30 : request.getDuration());
        quiz.setDescription(request.getDescription());
        quiz.setCreateTime(new Date());
        quiz.setUpdateTime(null);
        quiz.setDeleteTime(null);

        quiz = quizRepository.save(quiz);
        if (!request.getQuestions().isEmpty()) {
            for (QuestionRQ newReq : request.getQuestions()) {
                Question question = new Question();
                if (newReq != null) {
                    question.setQuizId(quiz.getId());
                    question.setQuestion(newReq.getQuestion());
                    question.setCorrectAnswer(newReq.getCorrectAnswer());
                    question.setOptions(newReq.getOptions());
                    question.setCreateTime(new Date());
                    question.setUpdateTime(null);
                    question.setDeleteTime(null);
                    quizList.add(question);
                    questionRepository.save(question);

                } else {
                    quizList.add(null);
                }
            }
        } else {
            throw QuizException.empty();
        }
        quiz.setQuestions(quizList);
        return quizRepository.save(quiz);
    }

    @Override
    public Quiz update(QuizUpdate request, String quizId) {
        Quiz entity = templateFactory.builder(Quiz.class).isNull(quizId, "deleteTime").findOne().orElseThrow(QuizException::notFound);;

        entity.setTitle(Objects.nonNull(request.getText()) ? request.getText() : entity.getTitle());
        entity.setDuration(request.getDuration() == 0 ? entity.getDuration() : request.getDuration());
        entity.setDescription(Objects.nonNull(request.getDescription()) ? request.getDescription() : entity.getDescription());
        entity.setUpdateTime(new Date());
        return quizRepository.save(entity);
    }

    @Override
    @Transactional
    public Quiz addQuesList(List<QuestionRQ> request, String quizId) {
        Quiz entity = templateFactory.builder(Quiz.class).isNull(quizId, "deleteTime").findOne().orElseThrow(QuizException::notFound);

        if (entity.getQuestions().isEmpty()) {
            for (QuestionRQ newReq : request) {
                Question question = new Question();
                question.setQuizId(entity.getId());
                question.setQuestion(newReq.getQuestion());
                question.setCorrectAnswer(newReq.getCorrectAnswer());
                question.setOptions(newReq.getOptions());
                question.setUpdateTime(new Date());
                questionRepository.save(question);

                entity.getQuestions().add(question);
            }
        } else {
            for (QuestionRQ newReq : request) {
                if (entity.getQuestions().stream().noneMatch(que ->
                    Objects.equals(que.getQuestion(), newReq.getQuestion()) &&
                        Objects.equals(que.getOptions(), newReq.getOptions()))) {
                    Question question = new Question();

                    question.setQuizId(entity.getId());
                    question.setQuestion(newReq.getQuestion());
                    question.setCorrectAnswer(newReq.getCorrectAnswer());
                    question.setOptions(newReq.getOptions());
                    question.setCreateTime(new Date());
                    question.setUpdateTime(null);
                    question.setDeleteTime(null);
                    questionRepository.save(question);
                    entity.getQuestions().add(question);
                }
            }
        }
        return quizRepository.save(entity);
    }

    @Override
    public void delete(String quizId) {
        Quiz entity = templateFactory.builder(Quiz.class).isNull(quizId, "deleteTime").findOne().orElseThrow(QuizException::notFound);
        entity.setDeleteTime(new Date());
        quizRepository.save(entity);
    }

    @Override
    @Transactional
    public void deletedAllQues(String quizId) {
        Quiz entity = templateFactory.builder(Quiz.class).isNull(quizId, "deleteTime").findOne().orElseThrow(QuizException::notFound);
        int check = entity.getQuestions().size();

        if (check != 0) {
            List<Question> question = entity.getQuestions();
            for (Question newReq : question) {
                if (Objects.equals(newReq.getQuizId(), entity.getId())) {
                    newReq.setDeleteTime(new Date());
                    entity.setQuestions(List.of());
                    questionRepository.save(newReq);
                }
            }
        } else {
            entity.setQuestions(List.of());
        }
        quizRepository.save(entity);
    }
}
