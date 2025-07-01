package org.example.tol.config.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.tol.domain.exception.UserException;
import org.example.tol.infrastructure.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserDetailService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    @SneakyThrows
    @Transactional
    public UserDetails loadUserByUsername(String username){
        try {
            return UserDetail.build(repository.findByUsername(username).orElseThrow(UserException::notFound));
        }catch (InternalAuthenticationServiceException e){
            throw new UserException(HttpStatus.BAD_REQUEST,"Username or password is incorrect");
        }
    }
}
