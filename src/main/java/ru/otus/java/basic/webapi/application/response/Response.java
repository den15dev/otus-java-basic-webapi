package ru.otus.java.basic.webapi.application.response;

public class Response extends AbstractResponse {
    public Response(String body) {
        this(HttpStatus.OK, body);
    }


    public Response(HttpStatus status, String body) {
        super(status, body);
    }
}
