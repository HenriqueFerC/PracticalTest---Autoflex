package com.example.demo.repository;

import com.example.demo.model.RawMaterialProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawMaterialProductRepository extends JpaRepository<RawMaterialProduct, Integer> {
}
