package ru.otus.java.basic.webapi.controller.error;

import ru.otus.java.basic.webapi.application.request.Request;
import ru.otus.java.basic.webapi.application.response.HttpStatus;
import ru.otus.java.basic.webapi.application.response.JsonResponse;
import ru.otus.java.basic.webapi.application.response.Response;
import ru.otus.java.basic.webapi.controller.Controller;

import java.util.Map;

public class NotFoundController extends Controller {
    public Response handle(Request request) {
        Map<String, String> out = Map.of("message", "Not Found");

        return new JsonResponse(HttpStatus.NOT_FOUND, out);
    }
}
