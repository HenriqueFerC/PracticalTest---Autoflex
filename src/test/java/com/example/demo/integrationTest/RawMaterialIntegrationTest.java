package com.example.demo.integrationTest;

import com.example.demo.dto.ProductDto.DetailsProductDto;
import com.example.demo.dto.ProductDto.RegisterProductDto;
import com.example.demo.dto.ProductDto.UpdateProductDto;
import com.example.demo.dto.RawMaterialDto.DetailsRawMaterialDto;
import com.example.demo.dto.RawMaterialDto.RegisterRawMaterialDto;
import com.example.demo.dto.RawMaterialDto.UpdateRawMaterialDto;
import com.example.demo.model.Product;
import com.example.demo.model.RawMaterial;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.RawMaterialRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.RawMaterialService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RawMaterialIntegrationTest {

    @Autowired
    private RawMaterialService rawMaterialService;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Test
    @Transactional
    public void createRawMaterialInDatabaseCase1() {
        RegisterRawMaterialDto rawMaterialDto = new RegisterRawMaterialDto("Wood", 54);

        RawMaterial rawMaterialSaved = rawMaterialService.saveRawMaterial(rawMaterialDto);
        Optional<RawMaterial> rawMaterial = rawMaterialRepository.findById(rawMaterialSaved.getId());

        assertEquals(rawMaterialSaved.getId(), rawMaterial.get().getId());
        assertThat(rawMaterial.get().getName()).isEqualTo("Wood");
        assertEquals(54, rawMaterial.get().getStock());
    }

    @Test
    @Transactional
    public void findRawMaterialInDatabaseByIdCase1() {
        RawMaterial rawMaterial = new RawMaterial(null, "Wood", 54, null);

        RawMaterial rawMaterialSaved = rawMaterialRepository.save(rawMaterial);

        DetailsRawMaterialDto rawMaterialFounded = rawMaterialService.findById(rawMaterialSaved.getId());

        assertThat(rawMaterialFounded.name()).isEqualTo("Wood");
        assertEquals(54, rawMaterialFounded.stock());
    }

    @Test
    @Transactional
    public void findRawMaterialInDatabaseByIdCase2() {
        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> rawMaterialService.findById(9999));
        assertThat(e.getReason()).isEqualTo("ID Raw Material Not Found!");
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Transactional
    public void updateRawMaterialInDatabaseCase1() {
        RawMaterial rawMaterial = new RawMaterial(null, "Wood", 54, null);
        RawMaterial rawMaterialSaved = rawMaterialRepository.save(rawMaterial);

        UpdateRawMaterialDto updateRawMaterial = new UpdateRawMaterialDto("BigWood", 52);
        DetailsRawMaterialDto rawMaterialUpdated = rawMaterialService.updateRawMaterial(rawMaterialSaved.getId(), updateRawMaterial);

        assertThat(rawMaterialUpdated.name()).isEqualTo("BigWood");
        assertEquals(52, rawMaterialUpdated.stock());
    }

    @Test
    @Transactional
    public void updateRawMaterialInDatabaseCase2() {
        UpdateRawMaterialDto updateRawMaterial = new UpdateRawMaterialDto("BigWood", 52);
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> rawMaterialService.updateRawMaterial(9999, updateRawMaterial));
        assertThat(e.getReason()).isEqualTo("ID Raw Material Not Found!");
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Transactional
    public void deleteRawMaterialInDatabaseCase1() {
        RawMaterial rawMaterialDto = new RawMaterial(null, "Wood", 54, null);
        RawMaterial rawMaterialSaved = rawMaterialRepository.save(rawMaterialDto);

        rawMaterialService.deleteById(rawMaterialSaved.getId());

        Optional<RawMaterial> rawMaterial = rawMaterialRepository.findById(rawMaterialSaved.getId());

        assertThat(rawMaterial).isEmpty();
    }

    @Test
    @Transactional
    public void deleteRawMaterialInDatabaseCase2() {
        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> rawMaterialService.findById(9999));
        assertThat(e.getReason()).isEqualTo("ID Raw Material Not Found!");
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
