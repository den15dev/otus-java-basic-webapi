package ru.otus.java.basic.webapi.service;

import ru.otus.java.basic.webapi.dto.category.CategoryInputDto;
import ru.otus.java.basic.webapi.dto.category.CategoryListDto;
import ru.otus.java.basic.webapi.dto.category.CategoryShowDto;
import ru.otus.java.basic.webapi.exception.DatabaseException;
import ru.otus.java.basic.webapi.exception.ResourceNotFoundException;
import ru.otus.java.basic.webapi.repository.CategoryRepository;

import java.sql.SQLException;
import java.util.List;

public class CategoryService {
    private final CategoryRepository categoryRepository;


    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public CategoryShowDto getCategoryById(int id) throws ResourceNotFoundException {
        try {
            CategoryShowDto category = categoryRepository.getCategoryById(id);

            if (category == null) {
                throw new ResourceNotFoundException("Category not found");
            }

            return category;

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get category " + id, e);
        }
    }


    public List<CategoryListDto> getCategories() {
        try {
            return categoryRepository.getCategories();

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get category list", e);
        }
    }


    public int addCategory(CategoryInputDto categoryData) {
        try {
            return categoryRepository.addCategory(categoryData);

        } catch (SQLException e) {
            throw new DatabaseException("Failed to add a category", e);
        }
    }


    public void updateCategory(int id, CategoryInputDto categoryData) throws ResourceNotFoundException {
        try {
            int updated = categoryRepository.updateCategory(id, categoryData);

            if (updated == 0) {
                throw new ResourceNotFoundException("Category not found");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to update category " + id, e);
        }
    }


    public void deleteCategory(int id) throws ResourceNotFoundException {
        try {
            if (!categoryRepository.deleteCategory(id)) {
                throw new ResourceNotFoundException("Category not found");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete category " + id, e);
        }
    }
}
