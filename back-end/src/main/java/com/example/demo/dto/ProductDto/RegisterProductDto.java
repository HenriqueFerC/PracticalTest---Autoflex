package com.example.demo.dto.ProductDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record RegisterProductDto(
        @NotBlank(message = "Product Name cannot be blank!")
        @Length(min = 2, max = 30, message = "Product Name must be between 2 and 30 letters long!")
        @Schema(description = "Should check if name is between 2 and 30 letters and isn't blank.")
        String name,
        @NotNull(message = "Product Value cannot be null!")
        @Min(value = 0, message = "Product Value cannot be negative!")
        @Schema(description = "Should check if value is highest then 0 and isn't null.")
        BigDecimal value
) {
}
