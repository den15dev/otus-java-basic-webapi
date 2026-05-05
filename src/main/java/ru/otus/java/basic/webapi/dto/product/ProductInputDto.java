package ru.otus.java.basic.webapi.dto.product;

import java.math.BigDecimal;

public record ProductInputDto(
        int categoryId,
        String name,
        String description,
        BigDecimal price
){}
