package org.example.tol.database.repository;

import org.example.tol.database.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question, String> {

}
