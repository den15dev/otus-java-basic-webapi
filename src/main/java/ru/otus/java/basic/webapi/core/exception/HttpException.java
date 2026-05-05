package ru.otus.java.basic.webapi.core.exception;

import ru.otus.java.basic.webapi.core.response.HttpStatus;

public abstract class HttpException extends Exception {
    private final HttpStatus status;


    protected HttpException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }


    public HttpStatus getStatus() {
        return status;
    }
}
