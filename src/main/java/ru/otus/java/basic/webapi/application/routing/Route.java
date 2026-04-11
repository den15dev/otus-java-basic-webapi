package ru.otus.java.basic.webapi.application.routing;

import ru.otus.java.basic.webapi.application.request.HttpMethod;
import ru.otus.java.basic.webapi.controller.Controller;

public abstract class Route {
    private final HttpMethod method;
    private final String path;
    private final Controller controller;


    public Route(String method, String path, Controller controller) {
        this.method = HttpMethod.valueOf(method.toUpperCase());
        this.path = path;
        this.controller = controller;
    }


    public HttpMethod getMethod() {
        return method;
    }


    public String getPath() {
        return path;
    }


    public Controller getController() {
        return controller;
    }
}
