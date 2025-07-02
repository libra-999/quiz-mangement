package org.example.tol.domain.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tol.config.jwt.Util;
import org.example.tol.config.security.UserDetail;
import org.example.tol.controller.request.Log;
import org.example.tol.controller.request.Register;
import org.example.tol.domain.exception.UserException;
import org.example.tol.infrastructure.entity.User;
import org.example.tol.infrastructure.redis.RedisService;
import org.example.tol.infrastructure.redis.config.RedisKey;
import org.example.tol.infrastructure.repository.UserRepository;
import org.example.tol.domain.service.AuthService;
import org.example.tol.util.constant.Gender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.example.tol.util.controller.ControllerHandler.toJsonString;
import static org.example.tol.util.exception.HandleIPClient.toIpAddr;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthFacade implements AuthService {

    private final static int MAX_ATTEMPT = 5;
    private static final long BLOCK_TIME_MINUTES = 3;

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final RedisService redisService;
    private final HttpServletRequest servletRequest;
    private final Util jwtUtil;

    @Override
    public Map<String, Object> login(Log request) {

//        if user try many times on login , (att>5)
        if (isBlocked(request.getUsername(), toIpAddr(servletRequest))) throw UserException.manyAttempt();
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
//          after 3 mins it will reset
            resetAttempt(request.getUsername(), toIpAddr(servletRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetail userDetail = (UserDetail) authentication.getPrincipal();
            String token = jwtUtil.generateJwtToken(authentication);
            String user = toJsonString(userDetail);

//      handle getValue in redis with lifetime 5mins
            redisService.getValue(RedisKey.VALUE_USER_LOGIN_TOKEN + token, toIpAddr(servletRequest), 3, TimeUnit.MINUTES);
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("user", user);
            return map;
        } catch (BadCredentialsException | JsonProcessingException e) {
            recordFailAttempt(request.getUsername(), toIpAddr(servletRequest));
            throw UserException.forbidden();
        }

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


    public void recordFailAttempt(String username, String ip) {
        redisService.increment(RedisKey.VALUE_NAME_FAIL_LOGIN + username + RedisKey.VALUE_IP + ip);
        redisService.expire(RedisKey.VALUE_NAME_FAIL_LOGIN + username + RedisKey.VALUE_IP + ip, BLOCK_TIME_MINUTES, TimeUnit.MINUTES);
    }

    public void resetAttempt(String username, String ip) {
        redisService.delete(RedisKey.VALUE_NAME_FAIL_LOGIN + username + RedisKey.VALUE_IP + ip);
    }

    public boolean isBlocked(String username, String ip) {
        String attStr = redisService.getValue(RedisKey.VALUE_NAME_FAIL_LOGIN + username + RedisKey.VALUE_IP + ip);
        int att = attStr == null ? 0 : Integer.parseInt(attStr);
        return att >= MAX_ATTEMPT;
    }
}
