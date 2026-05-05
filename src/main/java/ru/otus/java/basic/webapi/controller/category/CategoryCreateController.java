package ru.otus.java.basic.webapi.controller.category;

import com.google.gson.Gson;
import ru.otus.java.basic.webapi.core.request.Request;
import ru.otus.java.basic.webapi.core.response.HttpStatus;
import ru.otus.java.basic.webapi.core.response.JsonResponse;
import ru.otus.java.basic.webapi.core.response.Response;
import ru.otus.java.basic.webapi.controller.Controller;
import ru.otus.java.basic.webapi.dto.category.CategoryInputDto;
import ru.otus.java.basic.webapi.service.CategoryService;

import java.util.Map;

public class CategoryCreateController extends Controller {
    private final CategoryService categoryService;


    public CategoryCreateController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Response handle(Request request) {
        Gson gson = new Gson();
        CategoryInputDto categoryDto = gson.fromJson(request.getBody(), CategoryInputDto.class);
        int categoryId;

        categoryId = categoryService.addCategory(categoryDto);

        return new JsonResponse(
                HttpStatus.CREATED,
                Map.of("id", String.valueOf(categoryId))
        );
    }
}
