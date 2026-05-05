package ru.otus.java.basic.webapi.controller.product;

import ru.otus.java.basic.webapi.core.exception.HttpException;
import ru.otus.java.basic.webapi.core.request.Request;
import ru.otus.java.basic.webapi.core.response.JsonResponse;
import ru.otus.java.basic.webapi.core.response.Response;
import ru.otus.java.basic.webapi.controller.Controller;
import ru.otus.java.basic.webapi.dto.product.ProductFilter;
import ru.otus.java.basic.webapi.dto.product.ProductListDto;
import ru.otus.java.basic.webapi.service.ProductService;

import java.util.List;
import java.util.Map;


public class ProductListController extends Controller {
    private final ProductService productService;


    public ProductListController(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public Response handle(Request request) {
        try {
            ProductFilter filters = new ProductFilter(
                    request.parseQuery().getInt("categoryId"),
                    request.parseQuery().getBigDecimal("minPrice"),
                    request.parseQuery().getBigDecimal("maxPrice")
            );

            List<ProductListDto> products = productService.getProducts(filters);

            return new JsonResponse(products);

        } catch (HttpException e) {
            return new JsonResponse(
                    e.getStatus(),
                    Map.of("message", e.getMessage())
            );
        }
    }
}