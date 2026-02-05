package com.example.demo.service;

import com.example.demo.dto.RawMaterialDto.DetailsRawMaterialDto;
import com.example.demo.dto.RawMaterialDto.RegisterRawMaterialDto;
import com.example.demo.dto.RawMaterialDto.UpdateRawMaterialDto;
import com.example.demo.model.RawMaterial;
import com.example.demo.repository.RawMaterialRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RawMaterialServiceTest {

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @InjectMocks
    private RawMaterialService rawMaterialService;

    @Test
    @DisplayName("Should must be create Raw Material with successfully")
    void createRawMaterialCase() {
        RegisterRawMaterialDto rawMaterialDto = new RegisterRawMaterialDto("Madeira", 200);
        RawMaterial rawMaterialMock = new RawMaterial(1, "Madeira", 200, null);
        when(rawMaterialRepository.save(any(RawMaterial.class))).thenReturn(rawMaterialMock);

        RawMaterial rawMaterial = rawMaterialService.saveRawMaterial(rawMaterialDto);

        assertThat(rawMaterial.getName()).isEqualTo("Madeira");
    }

    @Test
    @DisplayName("Should must be find Raw Material by ID with successfully")
    void findRawMaterialByIdCase1() {
        int id = 1;
        RawMaterial rawMaterialMock = new RawMaterial(1, "Madeira", 200, null);
        when(rawMaterialRepository.findById(id)).thenReturn(Optional.of(rawMaterialMock));

        DetailsRawMaterialDto rawMaterialFounded = rawMaterialService.findById(id);

        assertThat(rawMaterialFounded.name()).isEqualTo("Madeira");
        assertEquals(200, rawMaterialFounded.stock());
    }

    @Test
    @DisplayName("Should not must be find Raw Material by ID: ID Not Found")
    void findRawMateriaByIdCase2() {
        when(rawMaterialRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> rawMaterialService.findById(1));
        assertThat(e.getReason()).isEqualTo("ID Raw Material Not Found!");
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Should must be update Raw Material with successfully")
    void updateRawMaterialCase1() {
        UpdateRawMaterialDto rawMaterialDto = new UpdateRawMaterialDto("Ferro", 400);
        RawMaterial rawMaterialMock = new RawMaterial(1, "Ferro", 400, null);

        when(rawMaterialRepository.save(any(RawMaterial.class))).thenReturn(rawMaterialMock);
        when(rawMaterialRepository.findById(1)).thenReturn(Optional.of(rawMaterialMock));

        DetailsRawMaterialDto rawMaterialUpdated = rawMaterialService.updateRawMaterial( 1 ,rawMaterialDto);

        assertThat(rawMaterialUpdated.name()).isEqualTo("Ferro");
        assertEquals(400, rawMaterialUpdated.stock());
    }
}
