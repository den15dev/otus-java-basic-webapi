package ru.otus.java.basic.webapi.controller.product;

import ru.otus.java.basic.webapi.core.exception.HttpException;
import ru.otus.java.basic.webapi.core.request.Request;
import ru.otus.java.basic.webapi.core.response.JsonResponse;
import ru.otus.java.basic.webapi.core.response.Response;
import ru.otus.java.basic.webapi.controller.Controller;
import ru.otus.java.basic.webapi.dto.product.ProductShowDto;
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

        } catch (HttpException e) {
            return new JsonResponse(
                    e.getStatus(),
                    Map.of("message", e.getMessage())
            );
        }
    }
}