package com.example.demo.controller;

import com.example.demo.dto.RawMaterialDto.DetailsRawMaterialDto;
import com.example.demo.dto.RawMaterialProduct.DetailsRawMaterialProductDto;
import com.example.demo.dto.RawMaterialProduct.RegisterRawMaterialProductDto;
import com.example.demo.model.RawMaterial;
import com.example.demo.model.RawMaterialProduct;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.RawMaterialProductRepository;
import com.example.demo.repository.RawMaterialRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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

    @GetMapping("necessaryRawMaterials/{idProduct}")
    public ResponseEntity<Page<DetailsRawMaterialDto>> listRawMaterialsNecessary(@PathVariable("idProduct") int id, Pageable pageable) {
        var product = productRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found!"));
        Page<DetailsRawMaterialDto> list = rawMaterialProductRepository.findByProduct(product, pageable)
                .map(rawMaterialProduct -> new DetailsRawMaterialDto(rawMaterialProduct.getRawMaterial()));
        return ResponseEntity.ok(list);
    }
}
