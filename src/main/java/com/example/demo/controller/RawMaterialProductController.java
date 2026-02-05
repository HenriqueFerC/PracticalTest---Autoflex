package com.example.demo.controller;

import com.example.demo.dto.RawMaterialDto.RawMaterialUsageDto;
import com.example.demo.dto.RawMaterialProduct.DetailsRawMaterialProductDto;
import com.example.demo.dto.RawMaterialProduct.RegisterRawMaterialProductDto;
import com.example.demo.service.RawMaterialProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


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
             @RequestBody RegisterRawMaterialProductDto rawMaterialProductDto,
             UriComponentsBuilder uriBuilder) {
        var saved = rawMaterialProductService.save(idProduct, idRawMaterial, rawMaterialProductDto);
        var uri = uriBuilder.path("rawMaterialProduct/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailsRawMaterialProductDto(saved));
    }

    @GetMapping("productsAvailable/{idRawMaterial}")
    public ResponseEntity<Page<RawMaterialUsageDto>> listProductsAvailableAndTotalValue(@PathVariable("idRawMaterial") int id, Pageable pageable) {
        var list = rawMaterialProductService.findByRawMaterial(id, pageable);
        return ResponseEntity.ok(list);
    }
}
