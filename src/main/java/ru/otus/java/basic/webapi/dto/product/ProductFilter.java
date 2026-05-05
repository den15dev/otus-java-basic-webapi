package ru.otus.java.basic.webapi.dto.product;

import java.math.BigDecimal;

public record ProductFilter(
        Integer categoryId,
        BigDecimal minPrice,
        BigDecimal maxPrice
){}
