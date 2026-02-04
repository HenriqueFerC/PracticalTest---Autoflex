package com.example.demo.controller;

import com.example.demo.dto.RawMaterialDto.DetailsRawMaterialDto;
import com.example.demo.dto.RawMaterialDto.RegisterRawMaterialDto;
import com.example.demo.dto.RawMaterialDto.UpdateRawMaterialDto;
import com.example.demo.model.RawMaterial;
import com.example.demo.repository.RawMaterialRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("rawMaterial")
public class RawMaterialController {

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialController(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @PostMapping("register")
    @Transactional
    public ResponseEntity<DetailsRawMaterialDto> registerRawMaterial(@RequestBody RegisterRawMaterialDto rawMaterialDto,
                                                                     UriComponentsBuilder uriBuilder) {
        var rawMaterial = new RawMaterial(rawMaterialDto);
        rawMaterialRepository.save(rawMaterial);
        var uri = uriBuilder.path("rawMaterial/{id}").buildAndExpand(rawMaterial.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailsRawMaterialDto(rawMaterial));
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<DetailsRawMaterialDto> findByIdDetailsRawMaterial(@PathVariable("id") int id) {
        var rawMaterial = rawMaterialRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found!"));
        return ResponseEntity.ok().body(new DetailsRawMaterialDto(rawMaterial));
    }

    @GetMapping("listRawMaterials")
    public ResponseEntity<List<DetailsRawMaterialDto>> listDetailsRawMaterials(Pageable pageable) {
        var list = rawMaterialRepository.findAll(pageable).stream().map(DetailsRawMaterialDto::new).toList();
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    @PutMapping("updateRawMaterial/{id}")
    @Transactional
    public ResponseEntity<DetailsRawMaterialDto> updateRawMaterial(@PathVariable("id") int id,
                                                                   @RequestBody UpdateRawMaterialDto rawMaterialDto) {
        var rawMaterial = rawMaterialRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found!"));
        rawMaterial.updateRawMaterial(
                rawMaterialDto.name() != null ? rawMaterialDto.name() : rawMaterial.getName(),
                rawMaterialDto.stock() != null ? rawMaterialDto.stock() : rawMaterial.getStock(),
                id
        );
        rawMaterialRepository.save(rawMaterial);
        return ResponseEntity.ok().body(new DetailsRawMaterialDto(rawMaterial));
    }

    @DeleteMapping("delete/{id}")
    @Transactional
    public ResponseEntity<Void> deleteRawMaterialById(@PathVariable("id") int id) {
        var rawMaterial = rawMaterialRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found!"));
        rawMaterialRepository.deleteById(rawMaterial.getId());
        return ResponseEntity.ok().build();
    }
}
