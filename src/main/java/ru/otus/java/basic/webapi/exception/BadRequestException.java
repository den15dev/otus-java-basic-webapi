package ru.otus.java.basic.webapi.exception;

import ru.otus.java.basic.webapi.application.exception.HttpException;
import ru.otus.java.basic.webapi.application.response.HttpStatus;

public class BadRequestException extends HttpException {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
