package com.example.demo.service;

import com.example.demo.dto.RawMaterialProduct.RawMaterialNecessaryToProduct;
import com.example.demo.dto.RawMaterialProduct.RawMaterialUsageDto;
import com.example.demo.dto.RawMaterialProduct.RegisterRawMaterialProductDto;
import com.example.demo.model.RawMaterialProduct;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.RawMaterialProductRepository;
import com.example.demo.repository.RawMaterialRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class RawMaterialProductService {

    public final RawMaterialProductRepository rawMaterialProductRepository;
    public final RawMaterialRepository rawMaterialRepository;
    public final ProductRepository productRepository;

    public RawMaterialProductService(RawMaterialProductRepository rawMaterialProductRepository, RawMaterialRepository rawMaterialRepository, ProductRepository productRepository) {
        this.rawMaterialProductRepository = rawMaterialProductRepository;
        this.rawMaterialRepository = rawMaterialRepository;
        this.productRepository = productRepository;
    }

    public RawMaterialProduct save(Integer idProduct, Integer idRawMaterial, RegisterRawMaterialProductDto rawMaterialProductDto) {
        var product = productRepository.findById(idProduct).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Product Not Found!"));
        var rawMaterial = rawMaterialRepository.findById(idRawMaterial).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Raw Material Not Found!"));
        var rawMaterialProduct = new RawMaterialProduct(rawMaterialProductDto, product, rawMaterial);
        rawMaterialProductRepository.save(rawMaterialProduct);
        return rawMaterialProduct;
    }


    public List<RawMaterialUsageDto> findByRawMaterial(Integer id, Pageable pageable) {
        var rawMaterial = rawMaterialRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Raw Material Not Found!"));
        return rawMaterialProductRepository.findByRawMaterial(rawMaterial, pageable)
                .map(rawMaterialProduct -> {
                    int possibleProducts = rawMaterial.getStock() / rawMaterialProduct.getQuantityToOneProduct();
                    BigDecimal totalValue = rawMaterialProduct.getProduct().getValue().multiply(BigDecimal.valueOf(possibleProducts));

                    return new RawMaterialUsageDto(
                            rawMaterialProduct.getProduct().getName(),
                            rawMaterialProduct.getProduct().getValue(),
                            rawMaterialProduct.getRawMaterial().getName(),
                            rawMaterialProduct.getRawMaterial().getStock(),
                            possibleProducts,
                            totalValue
                    );
                }).stream().sorted(Comparator.comparing(RawMaterialUsageDto::totalValue).reversed()).toList();
    }

    public Page<RawMaterialNecessaryToProduct> findByProduct(Integer id, Pageable pageable) {
        var product = productRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Product Not Found!"));
        return rawMaterialProductRepository.findByProduct(product, pageable)
                .map(rawMaterialProduct -> {
                    return new RawMaterialNecessaryToProduct(
                            product.getName(),
                            rawMaterialProduct.getRawMaterial().getName(),
                            rawMaterialProduct.getQuantityToOneProduct()
                    );
                });
    }
}
