package com.example.demo.dto.RawMaterialDto;

import java.math.BigDecimal;

public record RawMaterialUsageDto(String nameProduct, BigDecimal value, String nameRawMaterial, Integer stock,
                                  Integer quantityUsage, BigDecimal totalValue) {
}

