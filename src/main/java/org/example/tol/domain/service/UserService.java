package org.example.tol.domain.service;

import org.example.tol.controller.request.Client;
import org.example.tol.controller.request.ClientUpdate;
import org.example.tol.infrastructure.entity.User;
import org.example.tol.share.entity.Filter;
import org.example.tol.share.entity.PaginationQuery;
import org.example.tol.share.entity.Paging;

public interface UserService {

    Paging<User> list(PaginationQuery query, Filter filter);

    User view(String userId);

    User create(Client request);

    User update(String userId, ClientUpdate request);

    void delete(String userId);

    void deleteAll();

    void isActive(String userId, boolean status);
}
