package ru.otus.java.basic.webapi.dto.product;

import ru.otus.java.basic.webapi.dto.category.CategoryShowDto;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductShowDto(
        Integer id,
        CategoryShowDto category,
        String name,
        String description,
        BigDecimal price,
        Instant createdAt,
        Instant updatedAt
){}
