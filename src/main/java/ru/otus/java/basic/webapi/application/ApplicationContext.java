package ru.otus.java.basic.webapi.application;

import ru.otus.java.basic.webapi.controller.Controller;
import ru.otus.java.basic.webapi.controller.category.CategoryCreateController;
import ru.otus.java.basic.webapi.controller.product.*;
import ru.otus.java.basic.webapi.repository.CategoryRepository;
import ru.otus.java.basic.webapi.repository.ProductRepository;
import ru.otus.java.basic.webapi.service.CategoryService;
import ru.otus.java.basic.webapi.service.ProductService;

import java.util.HashMap;
import java.util.Map;


public class ApplicationContext {
    private final Map<Class<? extends Controller>, Controller> controllers;

    public ApplicationContext(Config config) {
        DataSource dataSource = new DataSource(config);
        ProductRepository productRepository = new ProductRepository(dataSource);
        ProductService productService = new ProductService(productRepository);

        CategoryRepository categoryRepository = new CategoryRepository(dataSource);
        CategoryService categoryService = new CategoryService(categoryRepository);

        this.controllers = new HashMap<>();

        this.controllers.put(CategoryCreateController.class, new CategoryCreateController(categoryService));

        this.controllers.put(ProductCreateController.class, new ProductCreateController(productService));
        this.controllers.put(ProductShowController.class, new ProductShowController(productService));
        this.controllers.put(ProductListController.class, new ProductListController(productService));
        this.controllers.put(ProductUpdateController.class, new ProductUpdateController(productService));
        this.controllers.put(ProductDeleteController.class, new ProductDeleteController(productService));
    }

    public Controller getController(Class<? extends Controller> controllerClass) {
        Controller controller = controllers.get(controllerClass);

        if (controller == null) {
            throw new IllegalArgumentException("Unknown controller: " + controllerClass.getName());
        }

        return controller;
    }
}
