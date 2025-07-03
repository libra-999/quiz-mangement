package org.example.tol.share.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class TemplateBuilder<T> {

    private final Class<T> entityClass;
    private final MongoTemplate mongoTemplate;
    private final List<Criteria> criteriaList = new ArrayList<>();
    private Sort sort = Sort.unsorted();

    //  handling with value as string
    public TemplateBuilder<T> withKeyword(String keyword, String... fields) {

        if (StringUtils.hasText(keyword) && fields.length > 0) {
            String escapedKeyword = Pattern.quote(keyword);
            List<Criteria> keywordCriteria = Arrays.stream(fields).map(field -> Criteria.where(field).regex(escapedKeyword, "i")).toList();
            criteriaList.add(new Criteria().orOperator(keywordCriteria.toArray(new Criteria[0])));
        }
        return this;
    }

    //  handling with value as integer
    public TemplateBuilder<T> withKeyword(Integer value, String... fields) {
        if (value != null && fields.length > 0) {
            List<Criteria> numberCriteria = Arrays.stream(fields).map(field -> Criteria.where(field).is(value)).toList();
            criteriaList.add(new Criteria().orOperator(numberCriteria.toArray(new Criteria[0])));
        }
        return this;
    }

    //    Call like: withFilters("admin", "is", "username")
    public TemplateBuilder<T> withFilters(String... fieldsAndValues) {
        for (int i = 0; i < fieldsAndValues.length; i += 3) {
            String field = fieldsAndValues[i];
            String operation = fieldsAndValues[i + 1];
            String value = fieldsAndValues[i + 2];
            if (!StringUtils.hasText(value)) continue;
            switch (operation.toLowerCase()) {
                case "ne" -> criteriaList.add(Criteria.where(field).ne(value)); // not equal
                case "gt" -> criteriaList.add(Criteria.where(field).gt(value)); // greater than
                case "lt" -> criteriaList.add(Criteria.where(field).lt(value)); // less than
                case "regex" -> criteriaList.add(Criteria.where(field).regex(value, "i")); // regex
                default -> criteriaList.add(Criteria.where(field).is(value)); // is
            }
        }
        return this;
    }

    //    handling with date filter by start and end
    public TemplateBuilder<T> withDate(Date start, Date end, String field) {
        if (Objects.nonNull(start) && Objects.nonNull(end) && StringUtils.hasText(field.trim())) {
            criteriaList.add(Criteria.where(field.trim()).gte(start).lte(end));
        }
        return this;
    }

    //    handling with value as boolean
    public TemplateBuilder<T> withStatus(boolean status, String field) {
        if (StringUtils.hasText(field)) {
            criteriaList.add(Criteria.where(field).is(status));
        }
        return this;
    }

    // fetching only records that have field is null
    public TemplateBuilder<T> deletedIsNull() {
        criteriaList.add(Criteria.where("deleted").is(null));
        return this;
    }

    public TemplateBuilder<T> withSort(Sort sort) {
        this.sort = sort;
        return this;
    }

    public Query build() {
        Query query = new Query();
        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }
        return query.with(sort);
    }

    public Page<T> execute(Pageable pageable) {
        Query query = build().with(pageable);
        List<T> content = mongoTemplate.find(query, entityClass);
        long total = mongoTemplate.count(query.skip(-1).limit(-1), entityClass);
        return new PageImpl<>(content, pageable, total);
    }
}
