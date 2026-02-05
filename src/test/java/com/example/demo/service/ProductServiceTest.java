package com.example.demo.service;

import com.example.demo.dto.ProductDto.DetailsProductDto;
import com.example.demo.dto.ProductDto.RegisterProductDto;
import com.example.demo.dto.ProductDto.UpdateProductDto;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Should must be create Product with successfully")
    void createProductCase() {
        RegisterProductDto productDto = new RegisterProductDto("Cadeira", BigDecimal.valueOf(1000));
        Product productMock = new Product(1, "Cadeira", BigDecimal.valueOf(1000), null);

        when(productRepository.save(any(Product.class))).thenReturn(productMock);
        Product product = productService.saveProduct(productDto);

        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("Cadeira");
        assertEquals(product.getValue() ,BigDecimal.valueOf(1000));
    }

    @Test
    @DisplayName("Should must be find Product by ID with successfully")
    void findProductByIdCase1() {
        int id = 1;
        Product productMock = new Product(1, "Cadeira", BigDecimal.valueOf(1000), null);
        when(productRepository.findById(id)).thenReturn(Optional.of(productMock));


        DetailsProductDto productFounded = productService.findById(id);


        assertThat(productFounded.name()).isEqualTo("Cadeira");
        assertEquals(productFounded.value(), BigDecimal.valueOf(1000));
    }

    @Test
    @DisplayName("Should not find Product by ID: NOT FOUND")
    void findProductByIdCase2() {
        int id = 2;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> productService.findById(id));
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(e.getReason()).isEqualTo("ID Product Not Found!");
    }

    @Test
    @DisplayName("Should must be a update Product successfully")
    void updateProductCase1() {
        int id = 1;
        UpdateProductDto productUpdated = new UpdateProductDto("Mesa", BigDecimal.valueOf(2000));
        Product productMock = new Product(1, "Mesa", BigDecimal.valueOf(2000), null);

        when(productRepository.save(any(Product.class))).thenReturn(productMock);
        when(productRepository.findById(id)).thenReturn(Optional.of(productMock));
        DetailsProductDto detailsProductDto = productService.updateProduct(1, productUpdated);

        assertThat(detailsProductDto.name()).isEqualTo("Mesa");
        assertEquals(detailsProductDto.value(), BigDecimal.valueOf(2000));
    }

    @Test
    @DisplayName("Should not must be a update Product: ID Not Found")
    void updateProductCase2() {
        int id = 2;
        UpdateProductDto productUpdated = new UpdateProductDto("Mesa", BigDecimal.valueOf(2000));

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> productService.updateProduct(id, productUpdated));
        assertThat(e.getReason()).isEqualTo( "ID Product Not Found!");
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Should must be delete Product with sucessfully")
    void deleteProductCase1() {
        Product productMock = new Product(1, "Mesa", BigDecimal.valueOf(2000), null);

        doNothing().when(productRepository).deleteById(1);
        when(productRepository.findById(1)).thenReturn(Optional.of(productMock));

        assertDoesNotThrow(() -> productService.deleteById(1));
    }

    @Test
    @DisplayName("Should not must be delete Product: ID Not Found")
    void deleteProductCase2() {
        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> productService.deleteById(1));
        assertThat(e.getReason()).isEqualTo("ID Product Not Found!");
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
