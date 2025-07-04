package org.example.tol.domain.exception;

import org.example.tol.share.exception.HttpException;
import org.springframework.http.HttpStatus;

public class AttemptException extends HttpException {

    public AttemptException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public static AttemptException notFound(){
        return new AttemptException(HttpStatus.NOT_FOUND,"Attempt not found");
    }
}
