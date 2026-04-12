package ru.otus.java.basic.webapi.entity;

import java.time.Instant;

public final class Product {
    private Integer id;
    private String name;
    private String description;
    private int price;
    private final Instant createdAt;
    private Instant updatedAt;

    
    public Product(
            String name,
            String description,
            int price
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }


    public Product(
            Integer id,
            String name,
            String description,
            int price,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public Integer getId() {
        return id;
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


    public int getPrice() {
        return price;
    }


    public void setPrice(int price) {
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
