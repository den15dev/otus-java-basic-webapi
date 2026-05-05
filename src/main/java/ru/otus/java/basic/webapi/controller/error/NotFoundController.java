package ru.otus.java.basic.webapi.controller.error;

import ru.otus.java.basic.webapi.core.request.Request;
import ru.otus.java.basic.webapi.core.response.HttpStatus;
import ru.otus.java.basic.webapi.core.response.JsonResponse;
import ru.otus.java.basic.webapi.core.response.Response;
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
