package ru.otus.java.basic.webapi.exception;

import ru.otus.java.basic.webapi.application.exception.HttpException;
import ru.otus.java.basic.webapi.application.response.HttpStatus;

public class ResourceNotFoundException extends HttpException {
    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
