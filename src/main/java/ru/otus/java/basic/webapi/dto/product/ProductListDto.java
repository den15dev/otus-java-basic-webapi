package ru.otus.java.basic.webapi.dto.product;

import ru.otus.java.basic.webapi.dto.category.CategoryShowDto;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductListDto(
        Integer id,
        CategoryShowDto category,
        String name,
        BigDecimal price,
        Instant createdAt,
        Instant updatedAt
){}
