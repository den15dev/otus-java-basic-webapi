package ru.otus.java.basic.webapi.controller.error;

import ru.otus.java.basic.webapi.application.request.Request;
import ru.otus.java.basic.webapi.application.response.HttpStatus;
import ru.otus.java.basic.webapi.application.response.JsonResponse;
import ru.otus.java.basic.webapi.application.response.Response;
import ru.otus.java.basic.webapi.controller.Controller;

import java.util.Map;

public class NotFoundController extends Controller {
    public Response handle(Request request) {
        return new JsonResponse(
                HttpStatus.NOT_FOUND,
                Map.of("message", "Not Found")
        );
    }
}
