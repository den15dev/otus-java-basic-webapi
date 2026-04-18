package ru.otus.java.basic.webapi.service;

import ru.otus.java.basic.webapi.dto.product.ProductFilter;
import ru.otus.java.basic.webapi.dto.product.ProductInputDto;
import ru.otus.java.basic.webapi.dto.product.ProductListDto;
import ru.otus.java.basic.webapi.dto.product.ProductShowDto;
import ru.otus.java.basic.webapi.exception.DatabaseException;
import ru.otus.java.basic.webapi.exception.ResourceNotFoundException;
import ru.otus.java.basic.webapi.repository.ProductRepository;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public ProductShowDto getProductWithDetailsById(int id) {
        try {
            ProductShowDto product = productRepository.getProductWithDetailsById(id);

            if (product == null) {
                throw new ResourceNotFoundException("Product not found");
            }

            return product;

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get a product " + id, e);
        }
    }


    public List<ProductListDto> getProducts(ProductFilter filter) {
        try {
            return productRepository.getProducts(filter);

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get product list", e);
        }
    }


    public int addProduct(ProductInputDto productDto) {
        try {
            return productRepository.addProduct(productDto);

        } catch (SQLException e) {
            throw new DatabaseException("Failed to add a product", e);
        }
    }
}
