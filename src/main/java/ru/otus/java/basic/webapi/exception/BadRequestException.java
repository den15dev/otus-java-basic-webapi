package ru.otus.java.basic.webapi.exception;

import ru.otus.java.basic.webapi.core.exception.HttpException;
import ru.otus.java.basic.webapi.core.response.HttpStatus;

public class BadRequestException extends HttpException {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
