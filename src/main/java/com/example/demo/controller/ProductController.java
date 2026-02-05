package com.example.demo.controller;

import com.example.demo.dto.ProductDto.DetailsProductDto;
import com.example.demo.dto.ProductDto.RegisterProductDto;
import com.example.demo.dto.ProductDto.UpdateProductDto;
import com.example.demo.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    public ResponseEntity<DetailsProductDto> registerProduct(@RequestBody RegisterProductDto productDto, UriComponentsBuilder uriBuilder) {
        var saved = productService.saveProduct(productDto);
        var uri = uriBuilder.path("product/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailsProductDto(saved));
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<DetailsProductDto> findByIdDetailsProduct(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(productService.findById(id));
    }

    @GetMapping("listProducts")
    public ResponseEntity<List<DetailsProductDto>> lisDetailsProduct(Pageable pageable) {
        var list = productService.findAll(pageable);
        if(list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    @PutMapping("updateProduct/{id}")
    @Transactional
    public ResponseEntity<DetailsProductDto> updateProduct(@PathVariable("id") Integer id, @RequestBody UpdateProductDto productDto) {
        return ResponseEntity.ok().body(productService.updateProduct(id, productDto));
    }

    @DeleteMapping("delete/{id}")
    @Transactional
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") Integer id) {;
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
