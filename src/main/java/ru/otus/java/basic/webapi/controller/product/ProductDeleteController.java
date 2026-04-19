package ru.otus.java.basic.webapi.controller.product;

import ru.otus.java.basic.webapi.application.exception.HttpException;
import ru.otus.java.basic.webapi.application.request.Request;
import ru.otus.java.basic.webapi.application.response.JsonResponse;
import ru.otus.java.basic.webapi.application.response.NoContentResponse;
import ru.otus.java.basic.webapi.application.response.Response;
import ru.otus.java.basic.webapi.controller.Controller;
import ru.otus.java.basic.webapi.service.ProductService;

import java.util.Map;

public class ProductDeleteController extends Controller {
    private final ProductService productService;


    public ProductDeleteController(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public Response handle(Request request) {
        int id = Integer.parseInt(request.getUrlParameter("id"));

        try {
            productService.deleteProduct(id);

            return new NoContentResponse();

        } catch (HttpException e) {
            return new JsonResponse(
                    e.getStatus(),
                    Map.of("message", e.getMessage())
            );
        }
    }
}
