package ru.otus.java.basic.webapi.route;

import ru.otus.java.basic.webapi.core.routing.RouteContainer;
import ru.otus.java.basic.webapi.controller.category.CategoryCreateController;
import ru.otus.java.basic.webapi.controller.product.*;


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
        add("PUT", "/products/{id}", ProductUpdateController.class);
        add("DELETE", "/products/{id}", ProductDeleteController.class);
    }
}
