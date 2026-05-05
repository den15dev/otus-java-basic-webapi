package ru.otus.java.basic.webapi.core.response;

public class NoContentResponse extends Response {
    public NoContentResponse() {
        super(HttpStatus.NO_CONTENT, null);
    }
}
