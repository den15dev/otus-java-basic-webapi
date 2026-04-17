package ru.otus.java.basic.webapi.dto.product;

public record ProductInputDto(
        int categoryId,
        String name,
        String description,
        double price
){}
