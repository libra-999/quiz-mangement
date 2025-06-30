package org.example.tol.database.repository;

import org.example.tol.database.entity.QuizAttempt;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuizAttemptRepository extends MongoRepository<QuizAttempt, String> {

}
