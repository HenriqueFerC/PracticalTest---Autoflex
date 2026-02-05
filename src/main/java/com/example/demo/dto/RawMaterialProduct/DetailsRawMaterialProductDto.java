package com.example.demo.dto.RawMaterialProduct;

import com.example.demo.dto.ProductDto.DetailsProductDto;
import com.example.demo.dto.RawMaterialDto.DetailsRawMaterialDto;
import com.example.demo.model.RawMaterialProduct;

public record DetailsRawMaterialProductDto(Integer quantityToOneProduct, DetailsProductDto productDto,
                                           DetailsRawMaterialDto rawMaterialDto) {
    public DetailsRawMaterialProductDto(RawMaterialProduct rawMaterialProduct) {
        this(rawMaterialProduct.getQuantityToOneProduct(), new DetailsProductDto(rawMaterialProduct.getProduct()),
                new DetailsRawMaterialDto(rawMaterialProduct.getRawMaterial()));
    }
}
