package com.example.demo.controller;

import com.example.demo.dto.RawMaterialProduct.DetailsRawMaterialProductDto;
import com.example.demo.dto.RawMaterialProduct.RegisterRawMaterialProductDto;
import com.example.demo.model.RawMaterialProduct;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.RawMaterialProductRepository;
import com.example.demo.repository.RawMaterialRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("rawMaterialProduct")
public class RawMaterialProductController {

    private final RawMaterialProductRepository rawMaterialProductRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final ProductRepository productRepository;


    public RawMaterialProductController(RawMaterialProductRepository rawMaterialProductRepository,
                                        RawMaterialRepository rawMaterialRepository,
                                        ProductRepository productRepository) {
        this.rawMaterialProductRepository = rawMaterialProductRepository;
        this.rawMaterialRepository = rawMaterialRepository;
        this.productRepository = productRepository;
    }


    @PostMapping("product/{idProduct}/rawMaterial/{idRawMaterial}")
    @Transactional
    public ResponseEntity<DetailsRawMaterialProductDto> registerRelationProductToRawMaterial
            (@PathVariable("idProduct") int idProduct,
             @PathVariable("idRawMaterial") int idRawMaterial,
             @RequestBody RegisterRawMaterialProductDto rawMaterialProductDto,
             UriComponentsBuilder uriBuilder) {
        var product = productRepository.findById(idProduct).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found!"));
        var rawMaterial = rawMaterialRepository.findById(idRawMaterial).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found!"));
        var rawMaterialProduct = new RawMaterialProduct(rawMaterialProductDto, product, rawMaterial);
        rawMaterialProductRepository.save(rawMaterialProduct);
        var uri = uriBuilder.path("rawMaterialProduct").buildAndExpand(rawMaterialProduct.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailsRawMaterialProductDto(rawMaterialProduct));
    }

}
