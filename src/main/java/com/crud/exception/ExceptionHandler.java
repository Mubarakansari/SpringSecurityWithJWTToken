package com.crud.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> aMap = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(err -> {
            aMap.put(err.getObjectName(), err.getDefaultMessage());
        });
        return new ExceptionResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Entity Validation failed", MessageType.ERROR, aMap);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFountException.class)
    public ExceptionResponse notFountException(NotFountException notFound) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND, notFound.getMessage(), MessageType.ERROR, null);
    }

    @Getter
    public static class ExceptionResponse {
        private HttpStatus httpStatus;
        private String message;
        private MessageType messageType;
        private Map<String, String> validator;

        public ExceptionResponse(HttpStatus httpStatus, String message, MessageType messageType, Map<String, String> validator) {
            this.httpStatus = httpStatus;
            this.message = message;
            this.messageType = messageType;
            this.validator = validator;
        }
    }

    @Getter
    public static enum MessageType {
        INFO, WARNING, ERROR, SUCCESS
    }
}
