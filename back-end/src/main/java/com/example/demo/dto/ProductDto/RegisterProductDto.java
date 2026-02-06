package com.example.demo.dto.ProductDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record RegisterProductDto(
        @NotBlank(message = "Product Name cannot be blank!")
        @Length(min = 2, max = 30, message = "Product Name must be between 2 and 30 letters long!")
        String name,
        @NotNull(message = "Product Value cannot be null!")
        @Min(value = 0, message = "Product Value cannot be negative!")
        BigDecimal value
) {
}
