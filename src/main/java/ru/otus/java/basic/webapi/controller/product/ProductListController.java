package ru.otus.java.basic.webapi.controller.product;

import ru.otus.java.basic.webapi.application.request.Request;
import ru.otus.java.basic.webapi.application.response.JsonResponse;
import ru.otus.java.basic.webapi.application.response.Response;
import ru.otus.java.basic.webapi.controller.Controller;
import ru.otus.java.basic.webapi.entity.Product;
import ru.otus.java.basic.webapi.service.ProductService;

import java.util.List;


public class ProductListController extends Controller {
    private final ProductService productService;


    public ProductListController(ProductService productService) {
        this.productService = productService;
    }


    public Response handle(Request request) {
        List<Product> products = productService.getProducts();

        return new JsonResponse(products);
    }
}