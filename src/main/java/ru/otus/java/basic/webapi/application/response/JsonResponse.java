package ru.otus.java.basic.webapi.application.response;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

public class JsonResponse extends Response {
    private static final Gson GSON = new Gson();


    public JsonResponse(Object data) {
        this(HttpStatus.OK, data);
    }


    public JsonResponse(HttpStatus status, Object data) {
        super(status, GSON.toJson(data));
        this.addHeader("Content-Type", "application/json");
    }


    @Override
    public byte[] getBytes() {
        return output().getBytes(StandardCharsets.UTF_8);
    }
}
