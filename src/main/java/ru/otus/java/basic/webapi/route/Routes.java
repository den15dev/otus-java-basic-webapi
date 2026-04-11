package ru.otus.java.basic.webapi.route;

import ru.otus.java.basic.webapi.application.routing.RouteContainer;
import ru.otus.java.basic.webapi.controller.HelloController;


public class Routes extends RouteContainer {
    public Routes() {
        super();
        load();
    }


    private void load() {
        add("GET", "/hello", new HelloController());
        add("GET", "/calculator", new HelloController());
    }
}
