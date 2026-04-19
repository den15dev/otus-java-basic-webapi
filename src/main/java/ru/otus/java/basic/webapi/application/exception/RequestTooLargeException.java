package ru.otus.java.basic.webapi.application.exception;

import ru.otus.java.basic.webapi.application.response.HttpStatus;

public class RequestTooLargeException extends HttpException {
    public RequestTooLargeException(int maxSize) {
        super(HttpStatus.REQUEST_TOO_LARGE,
                "Request exceeded max allowed size of " + maxSize + " bytes");
    }
}
