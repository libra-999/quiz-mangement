package org.example.tol.domain.exception;

import org.example.tol.share.exception.HttpException;
import org.springframework.http.HttpStatus;

public class QuizException extends HttpException {

    public QuizException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public static QuizException empty(){
        return new QuizException(HttpStatus.BAD_REQUEST,"Questions must be not empty");
    }

    public static QuizException notFound(){
        return new QuizException(HttpStatus.NOT_FOUND,"Quiz not found");
    }

    public static QuizException alreadyQuestionsExist(){
        return new QuizException(HttpStatus.CONFLICT,"Quiz already have questions");
    }
}
