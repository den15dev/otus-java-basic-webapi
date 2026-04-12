package ru.otus.java.basic.webapi.route;

import ru.otus.java.basic.webapi.application.routing.RouteContainer;
import ru.otus.java.basic.webapi.controller.product.ProductListController;
import ru.otus.java.basic.webapi.controller.product.ProductShowController;


public class Routes extends RouteContainer {
    public Routes() {
        super();
        load();
    }


    private void load() {
        add("GET", "/products", ProductShowController.class);
        add("GET", "/products/{id}", ProductListController.class);
    }
}
