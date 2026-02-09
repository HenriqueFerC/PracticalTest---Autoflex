package com.example.demo.dto.RawMaterialDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UpdateRawMaterialDto(
        @NotBlank(message = "Raw Material Name cannot be blank!")
        @Length(min = 2, max = 30, message = "Raw Material Name must be between 2 and 30 letters long!")
        @Schema(description = "Should check if name is between 2 and 30 letters and isn't blank.")
        String name,
        @NotNull(message = "Raw Material stock cannot be null!")
        @Min(value = 0, message = "Raw Material stock cannot be negative!")
        @Schema(description = "Should check if stock is highest then 0 and isn't null.")
        Integer stock
) {
}
