package ru.otus.java.basic.webapi.application;

import ru.otus.java.basic.webapi.application.response.Response;
import ru.otus.java.basic.webapi.application.routing.ParameterizedRoute;
import ru.otus.java.basic.webapi.controller.Controller;
import ru.otus.java.basic.webapi.controller.error.NotFoundController;
import ru.otus.java.basic.webapi.route.Routes;
import ru.otus.java.basic.webapi.application.request.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class Dispatcher {
    private final Routes routes;
    private final NotFoundController notFoundController;


    public Dispatcher() {
        this.routes = new Routes();
        this.notFoundController = new NotFoundController();
    }


    public void dispatch(HttpRequest request, OutputStream output) throws IOException {
        Response response;
        String key = request.getExactRouteKey();
        Controller controller = routes.getExactRoutes().get(key);
        if (controller != null) {
            response = controller.handle(request);
            output.write(response.getBytes());

            return;
        }

        for (ParameterizedRoute route : routes.getParameterizedRoutes()) {
            Map<String, String> urlParams = route.match(request.getMethod().name(), request.getUrl());
            if (urlParams != null) {
                request.setUrlParams(urlParams);
                response = route.getController().handle(request);
                output.write(response.getBytes());

                return;
            }
        }

        response = notFoundController.handle(request);
        output.write(response.getBytes());
    }
}
