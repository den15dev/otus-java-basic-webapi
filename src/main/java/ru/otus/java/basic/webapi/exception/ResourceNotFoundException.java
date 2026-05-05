package ru.otus.java.basic.webapi.exception;

import ru.otus.java.basic.webapi.core.exception.HttpException;
import ru.otus.java.basic.webapi.core.response.HttpStatus;

public class ResourceNotFoundException extends HttpException {
    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
