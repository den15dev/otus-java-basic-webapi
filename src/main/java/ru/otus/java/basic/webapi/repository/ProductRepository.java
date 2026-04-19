package ru.otus.java.basic.webapi.repository;

import ru.otus.java.basic.webapi.application.DataSource;
import ru.otus.java.basic.webapi.dto.category.CategoryShowDto;
import ru.otus.java.basic.webapi.dto.product.ProductFilter;
import ru.otus.java.basic.webapi.dto.product.ProductInputDto;
import ru.otus.java.basic.webapi.dto.product.ProductListDto;
import ru.otus.java.basic.webapi.dto.product.ProductShowDto;

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


    public List<ProductListDto> getProducts(ProductFilter filter) throws SQLException {
        StringBuilder sql = new StringBuilder("""
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
                    WHERE 1 = 1
                    """);

        List<Object> params = new ArrayList<>();

        if (filter.categoryId() != null) {
            sql.append(" AND category_id = ?");
            params.add(filter.categoryId());
        }

        if (filter.minPrice() != null) {
            sql.append(" AND price >= ?");
            params.add(filter.minPrice());
        }

        if (filter.maxPrice() != null) {
            sql.append(" AND price <= ?");
            params.add(filter.maxPrice());
        }

        sql.append(" ORDER BY created_at DESC");

        List<ProductListDto> products = new ArrayList<>();

        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())
        ) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CategoryShowDto category = new CategoryShowDto(
                            rs.getInt("category_id"),
                            rs.getString("category_name"),
                            rs.getString("category_slug")
                    );

                    products.add(new ProductListDto(
                            rs.getInt("id"),
                            category,
                            rs.getString("name"),
                            rs.getBigDecimal("price"),
                            rs.getTimestamp("created_at").toInstant(),
                            rs.getTimestamp("updated_at").toInstant()
                    ));
                }
            }
        }

        return products;
    }


    public int addProduct(ProductInputDto productData) throws SQLException {
        String sql = """
            INSERT INTO products (category_id, name, description, price, created_at, updated_at)
            VALUES (?, ?, ?, ?, NOW(), NOW())
            RETURNING id
            """;

        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, productData.categoryId());
            ps.setString(2, productData.name());
            ps.setString(3, productData.description());
            ps.setBigDecimal(4, productData.price());

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Failed to add a product: no id returned.");
                }

                return rs.getInt("id");
            }
        }
    }


    public int updateProduct(int id, ProductInputDto productData) throws SQLException {
        String sql = """
            UPDATE products
            SET category_id = ?,
                name = ?,
                description = ?,
                price = ?,
                updated_at = NOW()
            WHERE id = ?
            """;

        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, productData.categoryId());
            ps.setString(2, productData.name());
            ps.setString(3, productData.description());
            ps.setBigDecimal(4, productData.price());
            ps.setInt(5, id);

            return ps.executeUpdate();
        }
    }


    public boolean deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";

        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
