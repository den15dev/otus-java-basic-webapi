package ru.otus.java.basic.webapi.application.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.nio.charset.StandardCharsets;

public class JsonResponse extends Response {
    // A fix to correctly output createdAt and updatedAt fields in JSON which are of Instant type.
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>)
                    (src, typeOfSrc, context) -> new JsonPrimitive(src.truncatedTo(ChronoUnit.SECONDS).toString()))
            .create();


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
