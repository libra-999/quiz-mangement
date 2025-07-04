package org.example.tol.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.tol.controller.request.Log;
import org.example.tol.controller.request.RegisterRQ;
import org.example.tol.infrastructure.entity.User;

import java.util.Map;

public interface AuthService {

    Map<String, Object> login(Log request) throws JsonProcessingException;

    User register(RegisterRQ request);
}
