package org.example.tol.share.utility;


import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class TemplateFactory {

    private final MongoTemplate mongoTemplate;

    public TemplateFactory(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public <T> TemplateBuilder<T> builder(Class<T> entityClass) {
        return new TemplateBuilder<>(entityClass, mongoTemplate);
    }
}
