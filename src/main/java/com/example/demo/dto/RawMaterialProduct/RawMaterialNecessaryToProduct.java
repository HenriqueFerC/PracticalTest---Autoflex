package com.example.demo.dto.RawMaterialProduct;

public record RawMaterialNecessaryToProduct(
        String nameProduct,
        String nameRawMaterial,
        Integer quantityToOneProduct
) {
}
