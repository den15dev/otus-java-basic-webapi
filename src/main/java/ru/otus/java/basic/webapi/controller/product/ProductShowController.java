package ru.otus.java.basic.webapi.controller.product;

import ru.otus.java.basic.webapi.application.request.Request;
import ru.otus.java.basic.webapi.application.response.HttpStatus;
import ru.otus.java.basic.webapi.application.response.JsonResponse;
import ru.otus.java.basic.webapi.application.response.Response;
import ru.otus.java.basic.webapi.controller.Controller;
import ru.otus.java.basic.webapi.dto.product.ProductShowDto;
import ru.otus.java.basic.webapi.exception.ResourceNotFoundException;
import ru.otus.java.basic.webapi.service.ProductService;

import java.util.Map;


public class ProductShowController extends Controller {
    private final ProductService productService;


    public ProductShowController(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public Response handle(Request request) {
        int id = Integer.parseInt(request.getUrlParameter("id"));

        try {
            ProductShowDto product = productService.getProductWithDetailsById(id);

            return new JsonResponse(product);

        } catch (ResourceNotFoundException e) {
            return new JsonResponse(HttpStatus.NOT_FOUND, Map.of("message", e.getMessage()));
        }
    }
}