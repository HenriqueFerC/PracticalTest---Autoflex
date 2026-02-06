package com.example.demo.dto.RawMaterialProduct;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RegisterRawMaterialProductDto(
        @NotNull(message = "Quantity Raw Material to One Product cannot be null!")
        @Min(value = 1, message = "Quantity Raw Material to One Product cannot be less than 1!")
        Integer quantity
) {
}
