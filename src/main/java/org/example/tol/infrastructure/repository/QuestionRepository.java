package org.example.tol.infrastructure.repository;

import com.mongodb.RequestContext;
import org.example.tol.infrastructure.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question, String> {

    RequestContext findByQuizId(String quizId);
}
