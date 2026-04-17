package ru.otus.java.basic.webapi.repository;

import ru.otus.java.basic.webapi.application.DataSource;
import ru.otus.java.basic.webapi.dto.product.ProductInputDto;
import ru.otus.java.basic.webapi.entity.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class ProductRepository {
    private final DataSource ds;


    public ProductRepository(DataSource dataSource) {
        this.ds = dataSource;
    }


    public Product getProductById(int id) {
        return new Product(
                id,
                3,
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
                1,
                "Молоко",
                "Деревенское молоко, жирность 3,2%",
                105
        ));

        products.add(new Product(
                2,
                "Колбаса Докторская",
                "Докторская колбаса по ГОСТу 1936. Говядина высший сорт без жил.",
                235
        ));

        products.add(new Product(
                3,
                "Хлеб пшеничный",
                "Пшеничный формовой хлеб по ГОСТу из муки высшего сорта.",
                85
        ));

        return products;
    }


    public int addProduct(ProductInputDto productDto) throws SQLException {
        String sql = """
            INSERT INTO products (category_id, name, description, price, created_at, updated_at)
            VALUES (?, ?, ?, ?, NOW(), NOW())
            RETURNING id
            """;

        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, productDto.categoryId());
            ps.setString(2, productDto.name());
            ps.setString(3, productDto.description());
            ps.setDouble(4, productDto.price());

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Failed to add a product: no id returned.");
                }

                return rs.getInt("id");
            }
        }
    }
}
