package ru.otus.java.basic.webapi.controller.product;

import com.google.gson.Gson;
import ru.otus.java.basic.webapi.core.exception.HttpException;
import ru.otus.java.basic.webapi.core.request.Request;
import ru.otus.java.basic.webapi.core.response.JsonResponse;
import ru.otus.java.basic.webapi.core.response.Response;
import ru.otus.java.basic.webapi.controller.Controller;
import ru.otus.java.basic.webapi.dto.product.ProductInputDto;
import ru.otus.java.basic.webapi.service.ProductService;

import java.util.Map;

public class ProductUpdateController extends Controller {
    private final ProductService productService;


    public ProductUpdateController(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public Response handle(Request request) {
        int id = Integer.parseInt(request.getUrlParameter("id"));
        Gson gson = new Gson();
        ProductInputDto productDto = gson.fromJson(request.getBody(), ProductInputDto.class);

        try {
            productService.updateProduct(id, productDto);

            return new JsonResponse(Map.of("id", String.valueOf(id)));

        } catch (HttpException e) {
            return new JsonResponse(
                    e.getStatus(),
                    Map.of("message", e.getMessage())
            );
        }
    }
}
