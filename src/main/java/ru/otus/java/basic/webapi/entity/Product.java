package ru.otus.java.basic.webapi.entity;

import java.math.BigDecimal;
import java.time.Instant;

public final class Product {
    private Integer id;
    private int categoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private final Instant createdAt;
    private Instant updatedAt;

    
    public Product(
            int categoryId,
            String name,
            String description,
            BigDecimal price
    ) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }


    public Product(
            Integer id,
            int categoryId,
            String name,
            String description,
            BigDecimal price,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public Integer getId() {
        return id;
    }


    public int getCategoryId() {
        return categoryId;
    }


    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public BigDecimal getPrice() {
        return price;
    }


    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public Instant getCreatedAt() {
        return createdAt;
    }


    public Instant getUpdatedAt() {
        return updatedAt;
    }


    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
