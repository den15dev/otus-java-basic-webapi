package ru.otus.java.basic.webapi.application.routing;

import ru.otus.java.basic.webapi.controller.Controller;

public class ExactRoute extends Route {
    public ExactRoute(String method, String path, Controller controller) {
        super(method, path, controller);
    }
}
