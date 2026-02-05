package com.example.demo.service;

import com.example.demo.dto.ProductDto.DetailsProductDto;
import com.example.demo.dto.ProductDto.RegisterProductDto;
import com.example.demo.dto.ProductDto.UpdateProductDto;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(RegisterProductDto productDto){
        var product = new Product(productDto);
        productRepository.save(product);
        return product;
    }

    public DetailsProductDto findById(Integer id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Product Not Found!"));
        return new DetailsProductDto(product);
    }

    public List<DetailsProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).stream().map(DetailsProductDto::new).toList();
    }

    public DetailsProductDto updateProduct(Integer id, UpdateProductDto productDto) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Product Not Found!"));
        product.updateProduct(
                productDto.name() != null ? productDto.name() : product.getName(),
                productDto.value() != null ? productDto.value() : product.getValue(),
                id);
        productRepository.save(product);
        return new DetailsProductDto(product);
    }

    public void deleteById(Integer id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Product Not Found!"));
        productRepository.deleteById(product.getId());
    }
}
