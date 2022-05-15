package com.crud.config;

import com.crud.exception.ExceptionHandler;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GenericResponse {

    private HttpStatus httpStatus;
    private String message;
    private Object feed;
    private ExceptionHandler.MessageType messageType;

    public GenericResponse(HttpStatus httpStatus, String message, Object feed, ExceptionHandler.MessageType messageType) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.feed = feed;
        this.messageType = messageType;
    }
}
