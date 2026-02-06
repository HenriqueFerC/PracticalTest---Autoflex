package com.example.demo.model;

import com.example.demo.dto.RawMaterialProduct.RegisterRawMaterialProductDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_RAW_MATERIAL_PRODUCT")
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

    @Column(name = "RAW_MATERIAL_QUANTITY_TO_BUILD", nullable = false)
    private Integer quantityToOneProduct;

    public RawMaterialProduct(RegisterRawMaterialProductDto rawMaterialProductDto, Product product, RawMaterial rawMaterial) {
        quantityToOneProduct = rawMaterialProductDto.quantity();
        this.rawMaterial = rawMaterial;
        this.product = product;
    }
}
