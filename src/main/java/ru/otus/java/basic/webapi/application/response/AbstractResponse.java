package ru.otus.java.basic.webapi.application.response;

import java.util.HashMap;
import java.util.Map;


public abstract class AbstractResponse {
    private final HttpStatus status;
    private final String body;
    private final Map<String, String> headers;


    AbstractResponse(HttpStatus status, String body) {
        this.status = status;
        this.body = body;
        this.headers = new HashMap<>();
    }


    public HttpStatus getStatus() {
        return status;
    }


    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }


    public String output() {
        StringBuilder headersBuilder = new StringBuilder();

        for (String header : this.headers.keySet()) {
            headersBuilder.append(header)
                .append(": ")
                .append(this.headers.get(header))
                .append("\n");
        }

        return "HTTP/1.1 " + status.getCode() + " " + status.getMessage() + "\r\n" +
                headersBuilder + "\r\n" +
                body;
    }


    public byte[] getBytes() {
        return output().getBytes();
    }
}
