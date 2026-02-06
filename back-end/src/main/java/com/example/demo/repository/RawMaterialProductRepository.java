package com.example.demo.repository;

import com.example.demo.model.Product;
import com.example.demo.model.RawMaterial;
import com.example.demo.model.RawMaterialProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawMaterialProductRepository extends JpaRepository<RawMaterialProduct, Integer> {
    Page<RawMaterialProduct> findByProduct(Product product, Pageable pageable);

    Page<RawMaterialProduct> findByRawMaterial(RawMaterial rawMaterial, Pageable pageable);
}
