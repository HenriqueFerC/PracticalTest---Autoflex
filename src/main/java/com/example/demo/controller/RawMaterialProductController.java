package com.example.demo.controller;

import com.example.demo.dto.RawMaterialProduct.RawMaterialNecessaryToProduct;
import com.example.demo.dto.RawMaterialProduct.RawMaterialUsageDto;
import com.example.demo.dto.RawMaterialProduct.DetailsRawMaterialProductDto;
import com.example.demo.dto.RawMaterialProduct.RegisterRawMaterialProductDto;
import com.example.demo.service.RawMaterialProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping("rawMaterialProduct")
public class RawMaterialProductController {

    private final RawMaterialProductService rawMaterialProductService;

    public RawMaterialProductController(RawMaterialProductService rawMaterialProductService) {
        this.rawMaterialProductService = rawMaterialProductService;
    }


    @PostMapping("product/{idProduct}/rawMaterial/{idRawMaterial}")
    @Transactional
    public ResponseEntity<DetailsRawMaterialProductDto> registerRelationProductToRawMaterial
            (@PathVariable("idProduct") int idProduct,
             @PathVariable("idRawMaterial") int idRawMaterial,
             @RequestBody @Validated RegisterRawMaterialProductDto rawMaterialProductDto,
             UriComponentsBuilder uriBuilder) {
        var saved = rawMaterialProductService.save(idProduct, idRawMaterial, rawMaterialProductDto);
        var uri = uriBuilder.path("rawMaterialProduct/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailsRawMaterialProductDto(saved));
    }

    @GetMapping("productsAvailable/{idRawMaterial}")
    public ResponseEntity<List<RawMaterialUsageDto>> listProductsAvailableAndTotalValue(
            @PathVariable("idRawMaterial") int id, Pageable pageable) {
        var list = rawMaterialProductService.findByRawMaterial(id, pageable);
        if(list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("necessaryRawMaterial/{idProduct}")
    public ResponseEntity<Page<RawMaterialNecessaryToProduct>> listRawMaterialNecessaryToProduct(
            @PathVariable("idProduct") int id, Pageable pageable) {
        var list = rawMaterialProductService.findByProduct(id, pageable);
        if(list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }
}
