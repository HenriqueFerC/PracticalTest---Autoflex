package com.example.demo.dto.RawMaterialDto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record RegisterRawMaterialDto(
        @NotBlank(message = "Raw Material Name cannot be blank!")
        @Length(min = 2, max = 30, message = "Raw Material Name must be between 2 and 30 letters long!")
        String name,
        @NotNull(message = "Raw Material stock cannot be null!")
        @Min(value = 0, message = "Raw Material stock cannot be negative!")
        Integer stock
) {
}
