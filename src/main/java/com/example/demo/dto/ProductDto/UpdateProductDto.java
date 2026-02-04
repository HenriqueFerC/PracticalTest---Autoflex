package com.example.demo.dto.ProductDto;

import java.math.BigDecimal;

public record UpdateProductDto(
        String name,
        BigDecimal value) {
}
