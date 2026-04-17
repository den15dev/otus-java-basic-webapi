package ru.otus.java.basic.webapi.repository;

import ru.otus.java.basic.webapi.application.DataSource;
import ru.otus.java.basic.webapi.dto.category.CategoryShowDto;
import ru.otus.java.basic.webapi.dto.product.ProductInputDto;
import ru.otus.java.basic.webapi.dto.product.ProductShowDto;
import ru.otus.java.basic.webapi.entity.Product;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class ProductRepository {
    private final DataSource ds;


    public ProductRepository(DataSource dataSource) {
        this.ds = dataSource;
    }


    public ProductShowDto getProductWithDetailsById(int id) throws SQLException {
        String sql = """
                    SELECT
                        p.id,
                        p.category_id,
                        c.id AS category_id,
                        c.name AS category_name,
                        c.slug AS category_slug,
                        p.name,
                        p.description,
                        p.price,
                        p.created_at,
                        p.updated_at
                    FROM products p
                    JOIN categories c ON c.id = p.category_id
                    WHERE p.id = ?
                    """;

        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                CategoryShowDto category = new CategoryShowDto(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("category_slug")
                );

                return new ProductShowDto(
                        rs.getInt("id"),
                        category,
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBigDecimal("price"),
                        rs.getTimestamp("created_at").toInstant(),
                        rs.getTimestamp("updated_at").toInstant()
                );
            }
        }
    }


    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        products.add(new Product(
                1,
                "Молоко",
                "Деревенское молоко, жирность 3,2%",
                new BigDecimal(105)
        ));

        products.add(new Product(
                2,
                "Колбаса Докторская",
                "Докторская колбаса по ГОСТу 1936. Говядина высший сорт без жил.",
                new BigDecimal(235)
        ));

        products.add(new Product(
                3,
                "Хлеб пшеничный",
                "Пшеничный формовой хлеб по ГОСТу из муки высшего сорта.",
                new BigDecimal(85)
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
            ps.setBigDecimal(4, productDto.price());

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Failed to add a product: no id returned.");
                }

                return rs.getInt("id");
            }
        }
    }
}
