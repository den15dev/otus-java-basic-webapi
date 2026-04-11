package ru.otus.java.basic.webapi.controller.error;

import ru.otus.java.basic.webapi.application.request.HttpRequest;
import ru.otus.java.basic.webapi.application.response.HttpStatus;
import ru.otus.java.basic.webapi.application.response.JsonResponse;
import ru.otus.java.basic.webapi.application.response.Response;
import ru.otus.java.basic.webapi.controller.Controller;

public class ServerErrorController extends Controller {
    public Response handle(HttpRequest request) {
        var out = new Object() {
            final String message = "Internal Server Error";
        };

        return new JsonResponse(HttpStatus.SERVER_ERROR, out);
    }
}
