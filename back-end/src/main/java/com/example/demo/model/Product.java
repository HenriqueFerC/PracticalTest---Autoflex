package com.example.demo.model;

import com.example.demo.dto.ProductDto.RegisterProductDto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_PRODUCT")
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "ID_PRODUCT")
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(name = "NAME_PRODUCT", nullable = false, length = 30)
    private String name;

    @Column(name = "VALUE_PRODUCT", nullable = false)
    private BigDecimal value;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RawMaterialProduct> rawMaterialProduct;

    public Product(RegisterProductDto productDto) {
        name = productDto.name();
        value = productDto.value();
    }

    public void updateProduct(String name, BigDecimal value, Integer id) {
        this.id = id;
        this.name = name;
        this.value = value;
    }
}
