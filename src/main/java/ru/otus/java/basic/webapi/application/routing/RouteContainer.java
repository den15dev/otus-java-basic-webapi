package ru.otus.java.basic.webapi.application.routing;

import ru.otus.java.basic.webapi.controller.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteContainer {
    private final Map<String, Controller> exactRoutes;
    private final List<ParameterizedRoute> parameterRoutes;


    public RouteContainer() {
        this.exactRoutes = new HashMap<>();
        this.parameterRoutes = new ArrayList<>();
    }


    protected void add(String method, String path, Controller controller) {
        validate(method, path, controller);

        if (path.contains("{") && path.contains("}")) {
            parameterRoutes.add(new ParameterizedRoute(method, path, controller));

        } else {
            String key = buildExactRouteKey(method, path);
            exactRoutes.put(key, controller);
        }
    }


    private String buildExactRouteKey(String method, String path) {
        return method.toUpperCase() + " " + normalizePath(path);
    }


    private void validate(String method, String path, Controller controller) {
        if (method == null || method.isBlank()) {
            throw new IllegalArgumentException("HTTP method must not be empty");
        }
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("Path must not be empty");
        }
        if (controller == null) {
            throw new IllegalArgumentException("Controller must not be null");
        }
    }


    private String normalizePath(String path) {
        if (path.length() > 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        return path;
    }


    public Map<String, Controller> getExactRoutes() {
        return exactRoutes;
    }

    public List<ParameterizedRoute> getParameterizedRoutes() {
        return parameterRoutes;
    }
}
