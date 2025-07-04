package org.example.tol.domain.exception;


import org.example.tol.share.exception.HttpException;
import org.springframework.http.HttpStatus;

public class QuestionException extends HttpException {

    public QuestionException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public static QuestionException alreadyExist(){
        return new QuestionException(HttpStatus.BAD_REQUEST, "Question already exist");
    }

    public static QuestionException  notFound(){
        return new QuestionException(HttpStatus.NOT_FOUND,"Question not found");
    }

}
