package ru.otus.java.basic.webapi.application.response;

public class NoContentResponse extends Response {
    public NoContentResponse() {
        super(HttpStatus.NO_CONTENT, null);
    }
}
