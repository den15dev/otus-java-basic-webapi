package ru.otus.java.basic.webapi.application;

import ru.otus.java.basic.webapi.application.response.Response;
import ru.otus.java.basic.webapi.application.routing.Route;
import ru.otus.java.basic.webapi.controller.Controller;
import ru.otus.java.basic.webapi.controller.error.NotFoundController;
import ru.otus.java.basic.webapi.route.Routes;
import ru.otus.java.basic.webapi.application.request.Request;

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


    public Response dispatch(Request request) {
        String key = request.getExactRouteKey();
        Class<? extends Controller> controllerClass = routes.getRouteMap().get(key);

        if (controllerClass != null) {
            return appContext.getController(controllerClass).handle(request);
        }

        for (Route route : routes.getParameterizedRoutes()) {
            Map<String, String> urlParams = route.match(request.getMethod().name(), request.getUrl());

            if (urlParams != null) {
                request.setUrlParams(urlParams);
                controllerClass = route.getControllerClass();

                return appContext.getController(controllerClass).handle(request);
            }
        }

        return notFoundController.handle(null);
    }
}
