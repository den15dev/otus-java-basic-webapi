package ru.otus.java.basic.webapi.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.webapi.dto.product.CategoryInputDto;
import ru.otus.java.basic.webapi.exception.DatabaseException;
import ru.otus.java.basic.webapi.repository.CategoryRepository;

import java.sql.SQLException;

public class CategoryService {
    private final CategoryRepository categoryRepository;
    private static final Logger logger = LogManager.getLogger(CategoryService.class);


    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public int addCategory(CategoryInputDto categoryDto) {
        try {
            return categoryRepository.addCategory(categoryDto);

        } catch (SQLException e) {
            logger.error("Failed to add a category", e);
            throw new DatabaseException("Failed to add a category", e);
        }
    }
}
