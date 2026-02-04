package com.example.demo.model;

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

    @ManyToMany
    @JoinTable(name = "TB_RAW_MATERIAL_PRODUCT", joinColumns = @JoinColumn(name = "ID_RAW_MATERIAL"),
            inverseJoinColumns = @JoinColumn(name = "ID_PRODUCT"))
    private List<RawMaterial> rawMaterialList;

}
