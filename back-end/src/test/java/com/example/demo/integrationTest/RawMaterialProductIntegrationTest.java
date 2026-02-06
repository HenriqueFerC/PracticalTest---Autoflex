package com.example.demo.integrationTest;

import com.example.demo.dto.RawMaterialProduct.DetailsRawMaterialProductDto;
import com.example.demo.dto.RawMaterialProduct.RegisterRawMaterialProductDto;
import com.example.demo.model.Product;
import com.example.demo.model.RawMaterial;
import com.example.demo.model.RawMaterialProduct;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.RawMaterialProductRepository;
import com.example.demo.repository.RawMaterialRepository;
import com.example.demo.service.RawMaterialProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RawMaterialProductIntegrationTest {

    @Autowired
    private RawMaterialProductService rawMaterialProductService;

    @Autowired
    private RawMaterialProductRepository rawMaterialProductRepository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    public void createRawMaterialProductInDatabaseCase1() {
        RawMaterial rawMaterialDto = new RawMaterial(null, "Wood", 54, null);
        RawMaterial rawMaterialSaved = rawMaterialRepository.save(rawMaterialDto);

        Product productDto = new Product(null, "Table", BigDecimal.valueOf(200), null);
        Product productSaved = productRepository.save(productDto);

        RegisterRawMaterialProductDto rawMaterialProductDto = new RegisterRawMaterialProductDto(4);
        RawMaterialProduct rawMaterialProductCreated =
                rawMaterialProductService.save(productSaved.getId(), rawMaterialSaved.getId(), rawMaterialProductDto);

        assertEquals(4, rawMaterialProductCreated.getQuantityToOneProduct());
        assertThat(rawMaterialProductCreated.getProduct()).isEqualTo(productSaved);
        assertThat(rawMaterialProductCreated.getRawMaterial()).isEqualTo(rawMaterialSaved);
    }

    @Test
    @Transactional
    public void createRawMaterialProductInDatabaseCase2() {
        Product productDto = new Product(null, "Table", BigDecimal.valueOf(200), null);
        Product productSaved = productRepository.save(productDto);

        RegisterRawMaterialProductDto rawMaterialProductDto = new RegisterRawMaterialProductDto(4);

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                 () -> rawMaterialProductService.save(productSaved.getId(), 9999, rawMaterialProductDto));
        assertThat(e.getReason()).isEqualTo("ID Raw Material Not Found!");
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Transactional
    public void createRawMaterialProductInDatabaseCase3() {
        RawMaterial rawMaterialDto = new RawMaterial(null, "Wood", 54, null);
        RawMaterial rawMaterialSaved = rawMaterialRepository.save(rawMaterialDto);

        RegisterRawMaterialProductDto rawMaterialProductDto = new RegisterRawMaterialProductDto(4);

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> rawMaterialProductService.save(9999, rawMaterialSaved.getId(), rawMaterialProductDto));
        assertThat(e.getReason()).isEqualTo("ID Product Not Found!");
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
