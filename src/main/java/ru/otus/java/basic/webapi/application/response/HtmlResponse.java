package ru.otus.java.basic.webapi.application.response;

import java.nio.charset.StandardCharsets;

public class HtmlResponse extends Response {
    public HtmlResponse(String body) {
        this(HttpStatus.OK, body);
    }


    public HtmlResponse(HttpStatus status, String body) {
        super(status, body);
        this.addHeader("Content-Type", "text/html; charset=utf-8");
    }


    @Override
    public byte[] getBytes() {
        return output().getBytes(StandardCharsets.UTF_8);
    }
}
