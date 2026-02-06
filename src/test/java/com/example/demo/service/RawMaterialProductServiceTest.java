package com.example.demo.service;

import com.example.demo.dto.RawMaterialProduct.RegisterRawMaterialProductDto;
import com.example.demo.model.Product;
import com.example.demo.model.RawMaterial;
import com.example.demo.model.RawMaterialProduct;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.RawMaterialProductRepository;
import com.example.demo.repository.RawMaterialRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RawMaterialProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @Mock
    private RawMaterialProductRepository rawMaterialProductRepository;

    @InjectMocks
    private RawMaterialProductService rawMaterialProductService;

    @Test
    @DisplayName("Should must be create Raw Material - Product with sucessfully")
    void createRawMaterialProductCase1() {
        Product productMock = new Product(1, "Cadeira", BigDecimal.valueOf(1000), null);
        RawMaterial rawMaterialMock = new RawMaterial(1, "Ferro", 400, null);
        RegisterRawMaterialProductDto rawMaterialProductDto = new RegisterRawMaterialProductDto(4);
        RawMaterialProduct rawMaterialProductMock = new RawMaterialProduct(1, productMock, rawMaterialMock, 4);

        when(productRepository.findById(1)).thenReturn(Optional.of(productMock));
        when(rawMaterialRepository.findById(1)).thenReturn(Optional.of(rawMaterialMock));
        when(rawMaterialProductRepository.save(any(RawMaterialProduct.class))).thenReturn(rawMaterialProductMock);

        RawMaterialProduct rawMaterialProductCreated = rawMaterialProductService.save(1, 1, rawMaterialProductDto);

        assertEquals(4, rawMaterialProductCreated.getQuantityToOneProduct());
        assertThat(rawMaterialProductCreated.getRawMaterial()).isEqualTo(rawMaterialMock);
        assertThat(rawMaterialProductCreated.getProduct()).isEqualTo(productMock);
    }

}
