package com.example.demo.integrationTest;

import com.example.demo.dto.ProductDto.RegisterProductDto;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    public void createProductInDatabaseCase1() {
        RegisterProductDto productDto = new RegisterProductDto("Table", BigDecimal.valueOf(200));

        Product productSaved = productService.saveProduct(productDto);
        Optional<Product> product = productRepository.findById(productSaved.getId());

        assertEquals(productSaved.getId(), product.get().getId());
        assertThat(product.get().getName()).isEqualTo("Table");
        assertEquals(BigDecimal.valueOf(200), product.get().getValue());
    }

    
}
