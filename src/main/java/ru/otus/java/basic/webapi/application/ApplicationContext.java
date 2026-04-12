package ru.otus.java.basic.webapi.application;

import ru.otus.java.basic.webapi.controller.Controller;
import ru.otus.java.basic.webapi.controller.product.ProductListController;
import ru.otus.java.basic.webapi.controller.product.ProductShowController;
import ru.otus.java.basic.webapi.repository.ProductRepository;
import ru.otus.java.basic.webapi.service.ProductService;

import java.util.HashMap;
import java.util.Map;


public class ApplicationContext {
    private final Map<Class<? extends Controller>, Controller> controllers;

    public ApplicationContext() {
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);

        this.controllers = new HashMap<>();
        this.controllers.put(ProductShowController.class, new ProductShowController(productService));
        this.controllers.put(ProductListController.class, new ProductListController(productService));
    }

    public Controller getController(Class<? extends Controller> controllerClass) {
        Controller controller = controllers.get(controllerClass);

        if (controller == null) {
            throw new IllegalArgumentException("Unknown controller: " + controllerClass.getName());
        }

        return controller;
    }
}