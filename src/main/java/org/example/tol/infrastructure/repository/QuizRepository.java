package org.example.tol.infrastructure.repository;

import org.example.tol.infrastructure.entity.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuizRepository extends MongoRepository<Quiz, String> {

}
