package com.example.demo.dto.ProductDto;

import com.example.demo.model.Product;

import java.math.BigDecimal;

public record DetailsProductDto(String name, BigDecimal value) {
    public DetailsProductDto(Product product) {
        this(product.getName(), product.getValue());
    }
}
