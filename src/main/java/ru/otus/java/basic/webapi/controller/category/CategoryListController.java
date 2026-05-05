package ru.otus.java.basic.webapi.controller.category;

import ru.otus.java.basic.webapi.core.request.Request;
import ru.otus.java.basic.webapi.core.response.JsonResponse;
import ru.otus.java.basic.webapi.core.response.Response;
import ru.otus.java.basic.webapi.controller.Controller;
import ru.otus.java.basic.webapi.dto.category.CategoryListDto;
import ru.otus.java.basic.webapi.service.CategoryService;

import java.util.List;

public class CategoryListController extends Controller {
    private final CategoryService categoryService;


    public CategoryListController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Override
    public Response handle(Request request) {
        List<CategoryListDto> categories = categoryService.getCategories();

        return new JsonResponse(categories);
    }
}
