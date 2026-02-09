package com.example.demo.dto.RawMaterialDto;

import com.example.demo.model.RawMaterial;


public record DetailsRawMaterialDto(Integer id, String name, Integer stock) {
    public DetailsRawMaterialDto(RawMaterial rawMaterial) {
        this(rawMaterial.getId(), rawMaterial.getName(), rawMaterial.getStock());
    }
}
