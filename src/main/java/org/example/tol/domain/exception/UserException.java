package org.example.tol.domain.exception;

import org.example.tol.util.exception.HttpException;
import org.springframework.http.HttpStatus;

public class UserException extends HttpException {

    public UserException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public static UserException notFound() {
        return new UserException(HttpStatus.NOT_FOUND, "USERNAME_NOT_FOUND");
    }

    public static UserException forbidden() {
        return new UserException(HttpStatus.FORBIDDEN, "FORBIDDEN");
    }

    public static UserException alreadyExist() {
        return new UserException(HttpStatus.BAD_REQUEST, "USER_ALREADY_EXIST");
    }

    public static UserException manyAttempt(){
        return new UserException(HttpStatus.BAD_REQUEST, "TOO_MANY_ATTEMPT");
    }
}
