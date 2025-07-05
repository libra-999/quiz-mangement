package org.example.tol.domain.application;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.tol.controller.response.LeaderboardRS;
import org.example.tol.domain.exception.QuizException;
import org.example.tol.domain.service.LeaderBoardService;
import org.example.tol.infrastructure.entity.Question;
import org.example.tol.infrastructure.entity.Quiz;
import org.example.tol.infrastructure.redis.RedisService;
import org.example.tol.infrastructure.redis.config.RedisKey;
import org.example.tol.share.utility.TemplateFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeaderboardFacade implements LeaderBoardService {

    private final RedisService redisService;
    private final TemplateFactory templateFactory;

    @Override
    @SneakyThrows
    @Transactional(readOnly = true)
    public Object viewLeaderboard(String quizId, int limit) {

        Quiz entity = templateFactory.builder(Quiz.class)
            .isNull(quizId,"deleteTime").findOne().orElseThrow(QuizException::notFound);
        Set<String> topUser = redisService.topPlayer(RedisKey.ZSET_LEADERBOARD + quizId, limit);

        List<LeaderboardRS.Participate> joined = new ArrayList<>();
        for (String getUser : topUser) {
            Double score = redisService.score(RedisKey.ZSET_LEADERBOARD + quizId, getUser);
            if (score != null){

                joined.add(new LeaderboardRS.Participate(
                    getUser,score.intValue(),entity.getQuestions().stream().map(Question::getQuestion).toList()));
            }
        }

        LeaderboardRS response = new LeaderboardRS();
        response.setTitle(entity.getTitle());
        response.setDuration(entity.getDuration());
        response.setParticipates(joined);

        return response;
    }
}
