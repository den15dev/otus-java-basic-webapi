package ru.otus.java.basic.webapi.service;

import ru.otus.java.basic.webapi.dto.product.CategoryInputDto;
import ru.otus.java.basic.webapi.exception.DatabaseException;
import ru.otus.java.basic.webapi.repository.CategoryRepository;

import java.sql.SQLException;

public class CategoryService {
    private final CategoryRepository categoryRepository;


    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public int addCategory(CategoryInputDto categoryDto) {
        try {
            return categoryRepository.addCategory(categoryDto);

        } catch (SQLException e) {
            throw new DatabaseException("Failed to add a category", e);
        }
    }
}
