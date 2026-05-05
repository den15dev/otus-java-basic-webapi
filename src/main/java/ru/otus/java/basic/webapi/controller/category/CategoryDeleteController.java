package ru.otus.java.basic.webapi.controller.category;

import ru.otus.java.basic.webapi.core.exception.HttpException;
import ru.otus.java.basic.webapi.core.request.Request;
import ru.otus.java.basic.webapi.core.response.JsonResponse;
import ru.otus.java.basic.webapi.core.response.NoContentResponse;
import ru.otus.java.basic.webapi.core.response.Response;
import ru.otus.java.basic.webapi.controller.Controller;
import ru.otus.java.basic.webapi.service.CategoryService;

import java.util.Map;

public class CategoryDeleteController extends Controller {
    private final CategoryService categoryService;


    public CategoryDeleteController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Override
    public Response handle(Request request) {
        int id = Integer.parseInt(request.getUrlParameter("id"));

        try {
            categoryService.deleteCategory(id);

            return new NoContentResponse();

        } catch (HttpException e) {
            return new JsonResponse(
                    e.getStatus(),
                    Map.of("message", e.getMessage())
            );
        }
    }
}
