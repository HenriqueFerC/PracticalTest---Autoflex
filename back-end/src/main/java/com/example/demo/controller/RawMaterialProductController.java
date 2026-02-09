package com.example.demo.controller;

import com.example.demo.dto.RawMaterialProduct.DetailsRawMaterialProductDto;
import com.example.demo.dto.RawMaterialProduct.RawMaterialNecessaryToProduct;
import com.example.demo.dto.RawMaterialProduct.RawMaterialUsageDto;
import com.example.demo.dto.RawMaterialProduct.RegisterRawMaterialProductDto;
import com.example.demo.service.RawMaterialProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("rawMaterialProduct")
@Tag(name = "Raw Material - Product Controller", description = "API of Raw Material and Product, should register which raw material" +
        "is necessary for which product and then necessary quantity, should too list all raw material necessary to which product and list" +
        "products available for which raw material.")
public class RawMaterialProductController {
    
    private final RawMaterialProductService rawMaterialProductService;

    public RawMaterialProductController(RawMaterialProductService rawMaterialProductService) {
        this.rawMaterialProductService = rawMaterialProductService;
    }


    @PostMapping("product/{idProduct}/rawMaterial/{idRawMaterial}")
    @Transactional
    @Operation(summary = "Register Raw Material - Product", description = "Should associate Raw Material to Product and quantity necessary to make a product.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully associated raw material to product and registered quantity",
            content = @Content(schema = @Schema(implementation = DetailsRawMaterialProductDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request in JSON format."),
            @ApiResponse(responseCode = "404", description = "Product ID or Raw Material ID Not Found."),
            @ApiResponse(responseCode = "500", description = "Server error.")
    })
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
    @Operation(summary = "List Products available by Raw Material.", description = "Should return all products available and total value estimative in highest order" +
            " by a single raw material.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully returned all products available by a single raw material.",
            content = @Content(schema = @Schema(implementation = RawMaterialUsageDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "List Not Found (empty)."),
            @ApiResponse(responseCode = "500", description = "Server error.")
    })
    public ResponseEntity<List<RawMaterialUsageDto>> listProductsAvailableAndTotalValue(
            @PathVariable("idRawMaterial") int id, Pageable pageable) {
        var list = rawMaterialProductService.findByRawMaterial(id, pageable);
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("necessaryRawMaterial/{idProduct}")
    @Operation(summary = "Raw Materials necessary by a single product.", description = "Should return all raw materials necessary by a " +
            "single Product.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully returned all raw materials necessary by a single product.",
            content = @Content(schema = @Schema(implementation = RawMaterialNecessaryToProduct.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "List Not Found (empty)."),
            @ApiResponse(responseCode = "500", description = "Server error.")
    })
    public ResponseEntity<Page<RawMaterialNecessaryToProduct>> listRawMaterialNecessaryToProduct(
            @PathVariable("idProduct") int id, Pageable pageable) {
        var list = rawMaterialProductService.findByProduct(id, pageable);
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }
}
