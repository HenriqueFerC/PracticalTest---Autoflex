package com.example.demo.dto.ProductDto;

import java.math.BigDecimal;

public record RegisterProductDto(
        String name,
        BigDecimal value
) {
}
