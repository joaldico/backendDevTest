package com.itx.test.domain.model;

public record Product(
        String id,
        String name,
        Double price,
        Boolean availability) {
}