package ru.otus.java.basic.webapi.controller.category;

import com.google.gson.Gson;
import ru.otus.java.basic.webapi.core.exception.HttpException;
import ru.otus.java.basic.webapi.core.request.Request;
import ru.otus.java.basic.webapi.core.response.JsonResponse;
import ru.otus.java.basic.webapi.core.response.Response;
import ru.otus.java.basic.webapi.controller.Controller;
import ru.otus.java.basic.webapi.dto.category.CategoryInputDto;
import ru.otus.java.basic.webapi.service.CategoryService;

import java.util.Map;

public class CategoryUpdateController extends Controller {
    private final CategoryService categoryService;


    public CategoryUpdateController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Override
    public Response handle(Request request) {
        int id = Integer.parseInt(request.getUrlParameter("id"));
        Gson gson = new Gson();
        CategoryInputDto categoryData = gson.fromJson(request.getBody(), CategoryInputDto.class);

        try {
            categoryService.updateCategory(id, categoryData);

            return new JsonResponse(Map.of("id", String.valueOf(id)));

        } catch (HttpException e) {
            return new JsonResponse(
                    e.getStatus(),
                    Map.of("message", e.getMessage())
            );
        }
    }
}
