package com.example.demo.service;

import com.example.demo.dto.RawMaterialDto.DetailsRawMaterialDto;
import com.example.demo.dto.RawMaterialDto.RegisterRawMaterialDto;
import com.example.demo.dto.RawMaterialDto.UpdateRawMaterialDto;
import com.example.demo.model.RawMaterial;
import com.example.demo.repository.RawMaterialRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public RawMaterial saveRawMaterial(RegisterRawMaterialDto rawMaterialDto) {
        var rawMaterial = new RawMaterial(rawMaterialDto);
        rawMaterialRepository.save(rawMaterial);
        return rawMaterial;
    }

    public DetailsRawMaterialDto findById(Integer id) {
        var rawMaterial = rawMaterialRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Raw Material Not Found!"));
        return new DetailsRawMaterialDto(rawMaterial);
    }

    public List<DetailsRawMaterialDto> findAll(Pageable pageable) {
        return rawMaterialRepository.findAll(pageable).stream().map(DetailsRawMaterialDto::new).toList();
    }

    public DetailsRawMaterialDto updateRawMaterial(Integer id, UpdateRawMaterialDto rawMaterialDto) {
        var rawMaterial = rawMaterialRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Raw Material Not Found!"));
        rawMaterial.updateRawMaterial(
                rawMaterialDto.name() != null ? rawMaterialDto.name() : rawMaterial.getName(),
                rawMaterialDto.stock() != null ? rawMaterialDto.stock() : rawMaterial.getStock(),
                id
        );
        rawMaterialRepository.save(rawMaterial);
        return new DetailsRawMaterialDto(rawMaterial);
    }

    public void deleteById(Integer id) {
        var rawMaterial = rawMaterialRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Raw Material Not Found!"));
        rawMaterialRepository.deleteById(rawMaterial.getId());
    }
}
