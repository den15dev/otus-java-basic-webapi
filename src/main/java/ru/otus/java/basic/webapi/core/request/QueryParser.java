package ru.otus.java.basic.webapi.core.request;

import ru.otus.java.basic.webapi.exception.BadRequestException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public class QueryParser {
    private final Map<String, String> params;


    public QueryParser(Map<String, String> queryParams) {
        this.params = queryParams;
    }


    private String get(String name) {
        String value = params.get(name);
        return (value == null || value.isBlank()) ? null : value;
    }


    public Integer getInt(String name) throws BadRequestException {
        String value = get(name);
        if (value == null) {
            return null;
        }

        try {
            return Integer.valueOf(value);

        } catch (NumberFormatException e) {
            throw new BadRequestException("Query parameter '" + name + "' must be a valid integer.");
        }
    }


    public BigDecimal getBigDecimal(String name) throws BadRequestException {
        String value = get(name);
        if (value == null) {
            return null;
        }

        try {
            return new BigDecimal(value);

        } catch (NumberFormatException e) {
            throw new BadRequestException("Query parameter '" + name + "' must be a valid decimal number.");
        }
    }


    public Instant getInstant(String name) throws BadRequestException {
        String value = get(name);
        if (value == null) {
            return null;
        }

        try {
            return Instant.parse(value);

        } catch (Exception e) {
            throw new BadRequestException("Query parameter '" + name + "' must be a valid ISO-8601 datetime.");
        }
    }
}
