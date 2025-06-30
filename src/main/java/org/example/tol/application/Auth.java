package org.example.tol.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tol.bean.Log;
import org.example.tol.bean.Register;
import org.example.tol.database.entity.User;
import org.example.tol.database.repository.UserRepository;
import org.example.tol.service.AuthService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class Auth implements AuthService {
    private final UserRepository userRepository;

    @Override
    public User login(Log request) {
        return null;
    }

    @Override
    public User register(Register request) {
        return null;
    }
}
