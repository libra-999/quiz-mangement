package org.example.tol.database.repository;

import org.example.tol.database.entity.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuizRepository extends MongoRepository<Quiz, String> {

}
