package org.example.tol.domain.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tol.controller.request.Client;
import org.example.tol.domain.service.UserService;
import org.example.tol.infrastructure.entity.User;
import org.example.tol.infrastructure.repository.UserRepository;
import org.example.tol.util.entity.Filter;
import org.example.tol.util.entity.PageNumber;
import org.example.tol.util.entity.PaginationQuery;
import org.example.tol.util.entity.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserFacade implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encode;

    @Override
    public Paging<User> list(PaginationQuery query, Filter filter) {
        Page<User> page = repository.findAll(filter(query.getKeyword(), filter.getValue().get(0)), PageRequest.of(PageNumber.in(query.getPage()), query.getSize(), Sort.Direction.DESC, "username"));
        return Paging.<User>builder()
            .items(page.stream().toList())
            .page(PageNumber.out(page.getNumber()))
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .total(page.getTotalElements()).build();
    }

    @Override
    public User view(String userId) {
        return null;
    }

    @Override
    public User create(Client request) {
        return null;
    }

    @Override
    public User update(String userId, Client request) {
        return null;
    }

    @Override
    public User delete(String userId) {
        return null;
    }

    @Override
    public User deleteAll() {
        return null;
    }

    @Override
    public boolean isActive(String userId) {
        return false;
    }

    public static Query filter(String userId, String username){
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        if (Objects.nonNull(userId))
            criteriaList.add(Criteria.where("userId").is(userId));
        if (Objects.nonNull(username))
            criteriaList.add(Criteria.where("username").is(username));

        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }
        return query;
    }
}
