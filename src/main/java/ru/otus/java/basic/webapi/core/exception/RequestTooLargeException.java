package ru.otus.java.basic.webapi.core.exception;

import ru.otus.java.basic.webapi.core.response.HttpStatus;

public class RequestTooLargeException extends HttpException {
    public RequestTooLargeException(int maxSize) {
        super(HttpStatus.REQUEST_TOO_LARGE,
                "Request exceeded max allowed size of " + maxSize + " bytes");
    }
}
