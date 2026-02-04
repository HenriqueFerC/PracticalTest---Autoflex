package com.example.demo.controller;

import com.example.demo.dto.ProductDto.DetailsProductDto;
import com.example.demo.dto.ProductDto.RegisterProductDto;
import com.example.demo.dto.ProductDto.UpdateProductDto;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping("register")
    @Transactional
    public ResponseEntity<DetailsProductDto> registerProduct(@RequestBody RegisterProductDto productDto, UriComponentsBuilder uriBuilder) {
        var product = new Product(productDto);
        productRepository.save(product);
        var uri = uriBuilder.path("product/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailsProductDto(product));
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<DetailsProductDto> findByIdDetailsProduct(@PathVariable("id") int id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found!"));
        return ResponseEntity.ok().body(new DetailsProductDto(product));
    }

    @GetMapping("listProducts")
    public ResponseEntity<List<DetailsProductDto>> lisDetailsProduct(Pageable pageable) {
        var list = productRepository.findAll(pageable).stream().map(DetailsProductDto::new).toList();
        if(list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    @PutMapping("updateProduct/{id}")
    @Transactional
    public ResponseEntity<DetailsProductDto> updateProduct(@PathVariable("id") int id, @RequestBody UpdateProductDto productDto) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found!"));
        product.updateProduct(
                productDto.name() != null ? productDto.name() : product.getName(),
                productDto.value() != null ? productDto.value() : product.getValue(),
                id);
        productRepository.save(product);
        return ResponseEntity.ok().body(new DetailsProductDto(product));
    }

    @DeleteMapping("delete/{id}")
    @Transactional
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") int id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found!"));
        productRepository.deleteById(product.getId());
        return ResponseEntity.ok().build();
    }
}
