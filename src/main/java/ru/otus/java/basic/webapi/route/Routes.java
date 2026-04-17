package ru.otus.java.basic.webapi.route;

import ru.otus.java.basic.webapi.application.routing.RouteContainer;
import ru.otus.java.basic.webapi.controller.category.CategoryCreateController;
import ru.otus.java.basic.webapi.controller.product.ProductCreateController;
import ru.otus.java.basic.webapi.controller.product.ProductListController;
import ru.otus.java.basic.webapi.controller.product.ProductShowController;


public class Routes extends RouteContainer {
    public Routes() {
        super();
        load();
    }


    private void load() {
        add("POST", "/categories", CategoryCreateController.class);

        add("GET", "/products", ProductListController.class);
        add("GET", "/products/{id}", ProductShowController.class);
        add("POST", "/products", ProductCreateController.class);
    }
}
