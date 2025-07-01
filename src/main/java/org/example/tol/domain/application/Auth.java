package org.example.tol.domain.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tol.config.jwt.Util;
import org.example.tol.config.security.UserDetail;
import org.example.tol.controller.request.Log;
import org.example.tol.controller.request.Register;
import org.example.tol.domain.exception.UserException;
import org.example.tol.infrastructure.entity.User;
import org.example.tol.infrastructure.repository.UserRepository;
import org.example.tol.domain.service.AuthService;
import org.example.tol.util.constant.Gender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class Auth implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final Util jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    public Map<String, Object> login(Log request) throws JsonProcessingException {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        String token = jwtUtil.generateJwtToken(authentication);
        String user = objectMapper.writeValueAsString(userDetail);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", user);
        return map;
    }

    @Override
    public User register(Register request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw UserException.alreadyExist();
        }
        User user = new User();
        user.setPassword(encoder.encode(request.getPassword()));
        user.setGender(request.getGender() != 1 && request.getGender() != 2 ? Gender.NON.getType() : request.getGender());
        user.setUsername(request.getUsername());
        user.setName(request.getUsername());
        user.setDob(request.getDob());
        user.setCreateTime(new Date());
        user.setUpdateTime(null);
        return userRepository.save(user);
    }
}
