package org.example.tol.infrastructure.repository;

import org.example.tol.infrastructure.entity.QuizAttempt;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuizAttemptRepository extends MongoRepository<QuizAttempt, String> {

}
