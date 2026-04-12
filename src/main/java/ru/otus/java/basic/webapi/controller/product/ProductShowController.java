package ru.otus.java.basic.webapi.controller.product;

import ru.otus.java.basic.webapi.application.request.Request;
import ru.otus.java.basic.webapi.application.response.JsonResponse;
import ru.otus.java.basic.webapi.application.response.Response;
import ru.otus.java.basic.webapi.controller.Controller;
import ru.otus.java.basic.webapi.entity.Product;
import ru.otus.java.basic.webapi.service.ProductService;


public class ProductShowController extends Controller {
    private final ProductService productService;


    public ProductShowController(ProductService productService) {
        this.productService = productService;
    }


    public Response handle(Request request) {
        int id = Integer.parseInt(request.getUrlParameter("id"));
        Product product = productService.getProductById(id);

        return new JsonResponse(product);
    }
}