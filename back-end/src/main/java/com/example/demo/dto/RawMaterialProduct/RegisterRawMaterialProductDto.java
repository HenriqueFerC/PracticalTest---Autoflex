package com.example.demo.dto.RawMaterialProduct;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RegisterRawMaterialProductDto(
        @NotNull(message = "Quantity Raw Material to One Product cannot be null!")
        @Min(value = 1, message = "Quantity Raw Material to One Product cannot be less than 1!")
        @Schema(description = "Should check if quantity is highest then 1 and isn't null")
        Integer quantity
) {
}
