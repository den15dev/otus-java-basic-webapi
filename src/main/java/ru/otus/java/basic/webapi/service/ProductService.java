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


    public ProductShowDto getProductWithDetailsById(int id) throws ResourceNotFoundException {
        try {
            ProductShowDto product = productRepository.getProductWithDetailsById(id);

            if (product == null) {
                throw new ResourceNotFoundException("Product not found");
            }

            return product;

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get product " + id, e);
        }
    }


    public List<ProductListDto> getProducts(ProductFilter filter) {
        try {
            return productRepository.getProducts(filter);

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get product list", e);
        }
    }


    public int addProduct(ProductInputDto productData) {
        try {
            return productRepository.addProduct(productData);

        } catch (SQLException e) {
            throw new DatabaseException("Failed to add a product", e);
        }
    }


    public void updateProduct(int id, ProductInputDto productData) throws ResourceNotFoundException {
        try {
            int updated = productRepository.updateProduct(id, productData);

            if (updated == 0) {
                throw new ResourceNotFoundException("Product not found");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to update product " + id, e);
        }
    }


    public void deleteProduct(int id) throws ResourceNotFoundException {
        try {
            if (!productRepository.deleteProduct(id)) {
                throw new ResourceNotFoundException("Product not found");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete product " + id, e);
        }
    }
}
