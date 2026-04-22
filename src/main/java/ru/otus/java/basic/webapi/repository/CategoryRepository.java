package ru.otus.java.basic.webapi.repository;

import ru.otus.java.basic.webapi.core.database.DataSource;
import ru.otus.java.basic.webapi.dto.category.CategoryInputDto;
import ru.otus.java.basic.webapi.dto.category.CategoryListDto;
import ru.otus.java.basic.webapi.dto.category.CategoryShowDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private final DataSource ds;


    public CategoryRepository(DataSource dataSource) {
        this.ds = dataSource;
    }


    public CategoryShowDto getCategoryById(int id) throws SQLException {
        String sql = "SELECT id, name, slug FROM categories WHERE id = ?";

        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                return new CategoryShowDto(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("slug")
                );
            }
        }
    }


    public int addCategory(CategoryInputDto categoryData) throws SQLException {
        String sql = """
            INSERT INTO categories (name, slug)
            VALUES (?, ?)
            RETURNING id
            """;

        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, categoryData.name());
            ps.setString(2, categoryData.slug());

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Failed to add a category: no id returned.");
                }

                return rs.getInt("id");
            }
        }
    }


    public int updateCategory(int id, CategoryInputDto categoryData) throws SQLException {
        String sql = "UPDATE categories SET name = ?, slug = ? WHERE id = ?";

        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, categoryData.name());
            ps.setString(2, categoryData.slug());
            ps.setInt(3, id);

            return ps.executeUpdate();
        }
    }


    public List<CategoryListDto> getCategories() throws SQLException {
        List<CategoryListDto> categories = new ArrayList<>();
        String sql = "SELECT id, name, slug FROM categories";

        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                categories.add(new CategoryListDto(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("slug")
                ));
            }
        }

        return categories;
    }


    public boolean deleteCategory(int id) throws SQLException {
        String sql = "DELETE FROM categories WHERE id = ?";

        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
