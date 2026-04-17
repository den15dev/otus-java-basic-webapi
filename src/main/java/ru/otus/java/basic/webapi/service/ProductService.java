package ru.otus.java.basic.webapi.service;

import ru.otus.java.basic.webapi.dto.product.ProductInputDto;
import ru.otus.java.basic.webapi.entity.Product;
import ru.otus.java.basic.webapi.exception.DatabaseException;
import ru.otus.java.basic.webapi.repository.ProductRepository;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product getProductById(int id) {
        return productRepository.getProductById(id);
    }


    public List<Product> getProducts() {
        return productRepository.getProducts();
    }


    public int addProduct(ProductInputDto productDto) {
        try {
            return productRepository.addProduct(productDto);

        } catch (SQLException e) {
            throw new DatabaseException("Failed to add a product", e);
        }
    }
}
