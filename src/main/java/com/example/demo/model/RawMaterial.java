package com.example.demo.model;

import com.example.demo.dto.RawMaterialDto.RegisterRawMaterialDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Raw_Material")
public class RawMaterial {

    @Id
    @GeneratedValue
    @Column(name = "ID_RAW_MATERIAL")
    private Integer id;

    @Column(name = "NAME_RAW_MATERIAL", nullable = false, length = 30)
    private String name;

    @Column(name = "STOCK_RAW_MATERIAL", nullable = false)
    private Integer stock;

    @ManyToMany(mappedBy = "rawMaterialList")
    private List<Product> productsList;

    public RawMaterial(RegisterRawMaterialDto rawMaterialDto) {
        name = rawMaterialDto.name();
        stock = rawMaterialDto.stock();
    }
}
