package ru.otus.java.basic.webapi.repository;

import ru.otus.java.basic.webapi.application.DataSource;
import ru.otus.java.basic.webapi.dto.product.CategoryInputDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRepository {
    private final DataSource ds;


    public CategoryRepository(DataSource dataSource) {
        this.ds = dataSource;
    }


    public int addCategory(CategoryInputDto categoryDto) throws SQLException {
        String sql = """
            INSERT INTO categories (name, slug)
            VALUES (?, ?)
            RETURNING id
            """;

        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, categoryDto.name());
            ps.setString(2, categoryDto.slug());

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Failed to add a category: no id returned.");
                }

                return rs.getInt("id");
            }
        }
    }
}
