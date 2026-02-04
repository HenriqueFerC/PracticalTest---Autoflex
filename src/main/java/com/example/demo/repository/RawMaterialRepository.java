package com.example.demo.repository;

import com.example.demo.model.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Integer> {
}
