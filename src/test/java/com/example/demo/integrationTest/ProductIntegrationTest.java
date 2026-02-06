package com.example.demo.integrationTest;

import com.example.demo.dto.ProductDto.DetailsProductDto;
import com.example.demo.dto.ProductDto.RegisterProductDto;
import com.example.demo.dto.ProductDto.UpdateProductDto;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @Transactional
    public void findProductInDatabaseByIdCase1() {
        Product productDto = new Product(null, "Table", BigDecimal.valueOf(200), null);

        Product productSaved = productRepository.save(productDto);

        DetailsProductDto productFounded = productService.findById(productSaved.getId());

        assertThat(productFounded.name()).isEqualTo("Table");
        assertEquals(BigDecimal.valueOf(200), productFounded.value());
    }

    @Test
    @Transactional
    public void findProductInDatabaseByIdCase2() {
        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> productService.findById(9999));
        assertThat(e.getReason()).isEqualTo("ID Product Not Found!");
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Transactional
    public void updateProductInDatabaseCase1() {
        Product productDto = new Product(null, "Table", BigDecimal.valueOf(200), null);
        Product productSaved = productRepository.save(productDto);

        UpdateProductDto updateProduct = new UpdateProductDto("BigTable", BigDecimal.valueOf(300));
        DetailsProductDto productUpdated = productService.updateProduct(productSaved.getId(), updateProduct);

        assertThat(productUpdated.name()).isEqualTo("BigTable");
        assertEquals(BigDecimal.valueOf(300), productUpdated.value());
    }

    @Test
    @Transactional
    public void updateProductInDatabaseCase2() {
        UpdateProductDto updateProduct = new UpdateProductDto("BigTable", BigDecimal.valueOf(300));
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> productService.updateProduct(9999, updateProduct));
        assertThat(e.getReason()).isEqualTo("ID Product Not Found!");
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Transactional
    public void deleteProductInDatabaseCase1() {
        Product productDto = new Product(null, "Table", BigDecimal.valueOf(200), null);
        Product productSaved = productRepository.save(productDto);

        productService.deleteById(productSaved.getId());

        Optional<Product> product = productRepository.findById(productSaved.getId());

        assertThat(product).isEmpty();
    }

    @Test
    @Transactional
    public void deleteProductInDatabaseCase2() {
        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> productService.findById(9999));
        assertThat(e.getReason()).isEqualTo("ID Product Not Found!");
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
