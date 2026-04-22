package ru.otus.java.basic.webapi.controller.product;

import com.google.gson.Gson;
import ru.otus.java.basic.webapi.core.request.Request;
import ru.otus.java.basic.webapi.core.response.HttpStatus;
import ru.otus.java.basic.webapi.core.response.JsonResponse;
import ru.otus.java.basic.webapi.core.response.Response;
import ru.otus.java.basic.webapi.controller.Controller;
import ru.otus.java.basic.webapi.dto.product.ProductInputDto;
import ru.otus.java.basic.webapi.service.ProductService;

import java.util.Map;

public class ProductCreateController extends Controller {
    private final ProductService productService;


    public ProductCreateController(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public Response handle(Request request) {
        Gson gson = new Gson();
        ProductInputDto productDto = gson.fromJson(request.getBody(), ProductInputDto.class);
        int productId;

        productId = productService.addProduct(productDto);

        return new JsonResponse(
                HttpStatus.CREATED,
                Map.of("id", String.valueOf(productId))
        );
    }
}
