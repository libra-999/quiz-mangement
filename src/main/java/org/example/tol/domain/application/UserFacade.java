package org.example.tol.domain.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tol.controller.request.Client;
import org.example.tol.controller.request.ClientUpdate;
import org.example.tol.domain.exception.UserException;
import org.example.tol.domain.service.UserService;
import org.example.tol.infrastructure.entity.User;
import org.example.tol.infrastructure.repository.UserRepository;
import org.example.tol.share.constant.Gender;
import org.example.tol.share.entity.*;
import org.example.tol.share.utility.TemplateFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserFacade implements UserService {

    private final UserRepository repository;
    private final TemplateFactory templateFactory;
    private final SimpMessagingTemplate messageTemplate;
    private final PasswordEncoder encode;

    @Override
    @Transactional(readOnly = true)
    public Paging<User> list(PaginationQuery query, Filter filter) {

        Page<User> page = templateFactory.builder(User.class)
            .withKeyword(query.getKeyword(), "username")
            .withStatus(filter.isStatus(), "isActive")
            .withSort(Sort.by(Sort.Direction.DESC, "id"))
            .execute(PageRequest.of(PageNumber.in(query.getPage()), query.getSize()));

        return Paging.<User>builder()
            .items(page.stream().toList())
            .page(PageNumber.out(page.getNumber()))
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .total(page.getTotalElements()).build();
    }

    @Override
    public User view(String userId) {
        return repository.findById(userId).orElseThrow(UserException::notFound);
    }

    @Override
    public User create(Client request) {
        if (repository.findByUsername(request.getUsername()).isPresent()) throw UserException.alreadyExist();

        User entity = new User();
        entity.setName(request.getName());
        entity.setUsername(request.getUsername());
        entity.setPassword(encode.encode(request.getPassword()));
        entity.setDob(request.getDob());
        entity.setGender(request.getGender() != 1 && request.getGender() != 2 ? Gender.NON.getType() : request.getGender());
        entity.setCreateTime(new Date());

        return repository.save(entity);
    }

    @Override
    public User update(String userId, ClientUpdate request) {
        User user = repository.findById(userId).orElseThrow(UserException::notFound);
        user.setName(Objects.nonNull(request.getName()) && request.getName().isBlank() ? request.getName() : user.getUsername());
        user.setDob(Objects.nonNull(request.getDob()) && request.getDob().isBlank() ? request.getDob() : user.getDob());
        user.setGender(request.getGender());
        user.setUpdateTime(new Date());

        return repository.save(user);
    }

    @Override
    public void delete(String userId) {
        User user = repository.findById(userId).orElseThrow(UserException::notFound);
        repository.delete(user);
    }

    //  this method is important because it can be affect other users if someone call method without reason to use;
    //   Delete only old records in 1000 rows
    @Override
    public void deleteAll() {
        Page<User> userList = templateFactory.builder(User.class)
            .withFilters("username", "ne", "Admin")
            .withSort(Sort.by(Sort.Direction.ASC, "createTime"))
            .execute(PageRequest.of(PageNumber.in(1), 1000));
        repository.deleteAll(userList);
    }

    @Override
    public void isActive(String userId, boolean active) {
        User user = repository.findById(userId).orElseThrow(UserException::notFound);
        user.setActive(active);
        user.setUpdateTime(new Date());
    }


    @MessageMapping("/presence/online")
    public void userOnline(@Payload String userId){
        isActive(userId, true);
        messageTemplate.convertAndSend("/topic/presence",
            Map.of("status", "ONLINE" ));
    }

    @MessageMapping("/presence/offline")
    public void userOffline(@Payload String userId){
        isActive(userId, false);
        messageTemplate.convertAndSend("/topic/presence",
            Map.of("status", "OFFLINE"));

    }

}
