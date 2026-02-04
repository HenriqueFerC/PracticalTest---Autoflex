package com.example.demo.model;

import com.example.demo.dto.RawMaterialProduct.RegisterRawMaterialProductDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "RAW_MATERIAL_PRODUCT")
public class RawMaterialProduct {

    @Id
    @GeneratedValue
    @Column(name = "ID_RAW_MATERIAL_PRODUCT")
    @Setter(AccessLevel.NONE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUCT")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "ID_RAW_MATERIAL")
    private RawMaterial rawMaterial;

    @Column(name = "RAW_MATERIAL_QUANTITY", nullable = false)
    private Integer quantity;

    public RawMaterialProduct(RegisterRawMaterialProductDto rawMaterialProductDto, Product product, RawMaterial rawMaterial) {
        quantity = rawMaterialProductDto.quantity();
        this.rawMaterial = rawMaterial;
        this.product = product;
    }
}
