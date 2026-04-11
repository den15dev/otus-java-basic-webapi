package ru.otus.java.basic.webapi.application.response;

import java.nio.charset.StandardCharsets;

public class JsonResponse extends AbstractResponse {
    public JsonResponse(String body) {
        this(HttpStatus.OK, body);
    }


    public JsonResponse(HttpStatus status, String body) {
        super(status, body);
        this.addHeader("Content-Type", "application/json");
    }


    @Override
    public byte[] getBytes() {
        return output().getBytes(StandardCharsets.UTF_8);
    }
}
