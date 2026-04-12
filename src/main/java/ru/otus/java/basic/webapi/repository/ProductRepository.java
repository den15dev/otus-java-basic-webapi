package ru.otus.java.basic.webapi.repository;

import ru.otus.java.basic.webapi.entity.Product;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    public Product getProductById(int id) {
        return new Product(
                id,
                "Молоко",
                "Деревенское молоко, жирность 3,2%",
                105,
                Instant.now(),
                Instant.now()
        );
    }


    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        products.add(new Product(
                "Молоко",
                "Деревенское молоко, жирность 3,2%",
                105
        ));

        products.add(new Product(
                "Колбаса Докторская",
                "Докторская колбаса по ГОСТу 1936. Говядина высший сорт без жил.",
                235
        ));

        products.add(new Product(
                "Хлеб пшеничный",
                "Пшеничный формовой хлеб по ГОСТу из муки высшего сорта.",
                85
        ));

        return products;
    }
}
