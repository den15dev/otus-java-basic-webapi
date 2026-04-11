package ru.otus.java.basic.webapi.controller;

import ru.otus.java.basic.webapi.application.request.HttpRequest;
import ru.otus.java.basic.webapi.application.response.JsonResponse;
import ru.otus.java.basic.webapi.application.response.Response;

public class HelloController extends Controller {
    public Response handle(HttpRequest request) {
        var out = new Object() {
            final String message = "Hello World!";
        };

        return new JsonResponse(out);
    }
}
