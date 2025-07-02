package org.example.tol.domain.service;

import org.example.tol.controller.request.Client;
import org.example.tol.infrastructure.entity.User;
import org.example.tol.util.entity.Filter;
import org.example.tol.util.entity.PaginationQuery;
import org.example.tol.util.entity.Paging;

public interface UserService {

    Paging<User> list(PaginationQuery query, Filter filter);

    User view(String userId);

    User create(Client request);

    User update(String userId, Client request);

    User delete(String userId);

    User deleteAll();

    boolean isActive(String userId);
}
