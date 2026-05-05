package ru.otus.java.basic.webapi.core.response;

import java.util.HashMap;
import java.util.Map;


public class Response {
    private final HttpStatus status;
    private final String body;
    private final Map<String, String> headers;


    public Response() {
        this(HttpStatus.OK, null);
    }


    public Response(String body) {
        this(HttpStatus.OK, body);
    }


    public Response(HttpStatus status, String body) {
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
                    .append("\r\n");
        }

        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1 ")
                .append(status.getCode())
                .append(" ")
                .append(status.getMessage())
                .append("\r\n")
                .append(headersBuilder)
                .append("\r\n");

        if (body != null) {
            response.append(body);
        }

        return response.toString();
    }


    public byte[] getBytes() {
        return output().getBytes();
    }
}
