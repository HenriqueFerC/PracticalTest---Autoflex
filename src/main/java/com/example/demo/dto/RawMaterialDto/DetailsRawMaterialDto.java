package com.example.demo.dto.RawMaterialDto;

import com.example.demo.model.RawMaterial;


public record DetailsRawMaterialDto(String name, Integer stock) {
    public DetailsRawMaterialDto(RawMaterial rawMaterial) {
        this(rawMaterial.getName(), rawMaterial.getStock());
    }
}
