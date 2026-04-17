package ru.otus.java.basic.webapi.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.webapi.dto.product.ProductInputDto;
import ru.otus.java.basic.webapi.entity.Product;
import ru.otus.java.basic.webapi.exception.DatabaseException;
import ru.otus.java.basic.webapi.repository.ProductRepository;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;
    private static final Logger logger = LogManager.getLogger(ProductService.class);


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
            logger.error("Failed to add a product", e);
            throw new DatabaseException("Failed to add a product", e);
        }
    }
}
