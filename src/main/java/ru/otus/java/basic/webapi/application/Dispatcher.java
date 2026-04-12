package ru.otus.java.basic.webapi.application;

import ru.otus.java.basic.webapi.application.response.Response;
import ru.otus.java.basic.webapi.application.routing.Route;
import ru.otus.java.basic.webapi.controller.Controller;
import ru.otus.java.basic.webapi.controller.error.NotFoundController;
import ru.otus.java.basic.webapi.route.Routes;
import ru.otus.java.basic.webapi.application.request.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class Dispatcher {
    private final Routes routes;
    private final ApplicationContext appContext;
    private final NotFoundController notFoundController;


    public Dispatcher() {
        this.routes = new Routes();
        this.appContext = new ApplicationContext();
        this.notFoundController = new NotFoundController();
    }


    public void dispatch(HttpRequest request, OutputStream output) throws IOException {
        Response response;
        String key = request.getExactRouteKey();
        Class<? extends Controller> controllerClass = routes.getRouteMap().get(key);

        if (controllerClass != null) {
            response = appContext
                .getController(controllerClass)
                .handle(request);
            output.write(response.getBytes());

            return;
        }

        for (Route route : routes.getParameterizedRoutes()) {
            Map<String, String> urlParams = route.match(request.getMethod().name(), request.getUrl());

            if (urlParams != null) {
                request.setUrlParams(urlParams);
                controllerClass = route.getControllerClass();
                response = appContext
                        .getController(controllerClass)
                        .handle(request);
                output.write(response.getBytes());

                return;
            }
        }

        response = notFoundController.handle(request);
        output.write(response.getBytes());
    }
}
