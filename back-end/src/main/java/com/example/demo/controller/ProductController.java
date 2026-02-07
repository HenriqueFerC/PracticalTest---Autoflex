package com.example.demo.controller;

import com.example.demo.dto.ProductDto.DetailsProductDto;
import com.example.demo.dto.ProductDto.RegisterProductDto;
import com.example.demo.dto.ProductDto.UpdateProductDto;
import com.example.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("register")
    @Transactional
    @Operation(summary = "Register Product", description = "Should register product in database.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product successfully registered.",
                    content = @Content(schema = @Schema(implementation = DetailsProductDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request in JSON format."),
            @ApiResponse(responseCode = "500", description = "Server error.")
    })
    public ResponseEntity<DetailsProductDto> registerProduct(@RequestBody @Validated RegisterProductDto productDto, UriComponentsBuilder uriBuilder) {
        var saved = productService.saveProduct(productDto);
        var uri = uriBuilder.path("product/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailsProductDto(saved));
    }

    @GetMapping("findById/{id}")
    @Operation(summary = "Details Product", description = "Should find product by id and then details is returned.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product successfully returned.",
                    content = @Content(schema = @Schema(implementation = DetailsProductDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Product ID Not Found."),
            @ApiResponse(responseCode = "500", description = "Server error.")
    })
    public ResponseEntity<DetailsProductDto> findByIdDetailsProduct(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(productService.findById(id));
    }

    @GetMapping("listProducts")
    @Operation(summary = "List Products", description = "Should return list products.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List Products successfully returned.",
                    content = @Content(schema = @Schema(implementation = DetailsProductDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "List Product Not Found (empty)."),
            @ApiResponse(responseCode = "500", description = "ServerError.")
    })
    public ResponseEntity<List<DetailsProductDto>> lisDetailsProduct(Pageable pageable) {
        var list = productService.findAll(pageable);
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    @PutMapping("updateProduct/{id}")
    @Transactional
    @Operation(summary = "Update Product", description = "Should update product by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product successfully updated.",
                    content = @Content(schema = @Schema(implementation = DetailsProductDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request in JSON format."),
            @ApiResponse(responseCode = "404", description = "Product ID Not Found."),
            @ApiResponse(responseCode = "500", description = "Server error.")
    })
    public ResponseEntity<DetailsProductDto> updateProduct(@PathVariable("id") Integer id, @RequestBody @Validated UpdateProductDto productDto) {
        return ResponseEntity.ok().body(productService.updateProduct(id, productDto));
    }

    @DeleteMapping("delete/{id}")
    @Transactional
    @Operation(summary = "Delete Product", description = "Should delete product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Product ID Not Found."),
            @ApiResponse(responseCode = "500", description = "Server Error.")
    })
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") Integer id) {
        ;
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
