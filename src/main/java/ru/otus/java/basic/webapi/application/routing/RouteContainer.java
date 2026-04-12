package ru.otus.java.basic.webapi.application.routing;

import ru.otus.java.basic.webapi.controller.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteContainer {
    private final Map<String, Class<? extends Controller>> routeMap;
    private final List<Route> parameterizedRoutes;


    public RouteContainer() {
        this.routeMap = new HashMap<>();
        this.parameterizedRoutes = new ArrayList<>();
    }


    protected void add(String method, String path, Class<? extends Controller> controllerClass) {
        validate(method, path, controllerClass);

        if (path.contains("{") && path.contains("}")) {
            parameterizedRoutes.add(new Route(method, path, controllerClass));

        } else {
            String key = buildExactRouteKey(method, path);
            routeMap.put(key, controllerClass);
        }
    }


    private String buildExactRouteKey(String method, String path) {
        return method.toUpperCase() + " " + normalizePath(path);
    }


    private void validate(String method, String path, Class<? extends Controller> controllerClass) {
        if (method == null || method.isBlank()) {
            throw new IllegalArgumentException("HTTP method must not be empty");
        }
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("Path must not be empty");
        }
        if (controllerClass == null) {
            throw new IllegalArgumentException("Controller must not be null");
        }
    }


    private String normalizePath(String path) {
        if (path.length() > 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        return path;
    }


    public Map<String, Class<? extends Controller>> getRouteMap() {
        return routeMap;
    }

    public List<Route> getParameterizedRoutes() {
        return parameterizedRoutes;
    }
}
