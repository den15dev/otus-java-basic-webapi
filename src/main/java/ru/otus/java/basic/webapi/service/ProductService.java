package ru.otus.java.basic.webapi.service;

import ru.otus.java.basic.webapi.entity.Product;
import ru.otus.java.basic.webapi.repository.ProductRepository;

import java.util.List;

public class ProductService {
    ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product getProductById(int id) {
        return productRepository.getProductById(id);
    }


    public List<Product> getProducts() {
        return productRepository.getProducts();
    }
}
