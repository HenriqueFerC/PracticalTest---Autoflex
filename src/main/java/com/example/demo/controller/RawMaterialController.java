package com.example.demo.controller;

import com.example.demo.dto.RawMaterialDto.DetailsRawMaterialDto;
import com.example.demo.dto.RawMaterialDto.RegisterRawMaterialDto;
import com.example.demo.dto.RawMaterialDto.UpdateRawMaterialDto;
import com.example.demo.service.RawMaterialService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("rawMaterial")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @PostMapping("register")
    @Transactional
    public ResponseEntity<DetailsRawMaterialDto> registerRawMaterial(@RequestBody RegisterRawMaterialDto rawMaterialDto,
                                                                     UriComponentsBuilder uriBuilder) {
        var saved = rawMaterialService.saveRawMaterial(rawMaterialDto);
        var uri = uriBuilder.path("rawMaterial/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailsRawMaterialDto(saved));
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<DetailsRawMaterialDto> findByIdDetailsRawMaterial(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(rawMaterialService.findById(id));
    }

    @GetMapping("listRawMaterials")
    public ResponseEntity<List<DetailsRawMaterialDto>> listDetailsRawMaterials(Pageable pageable) {
        var list = rawMaterialService.findAll(pageable);
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    @PutMapping("updateRawMaterial/{id}")
    @Transactional
    public ResponseEntity<DetailsRawMaterialDto> updateRawMaterial(@PathVariable("id") Integer id,
                                                                   @RequestBody UpdateRawMaterialDto rawMaterialDto) {
        return ResponseEntity.ok().body(rawMaterialService.updateRawMaterial(id, rawMaterialDto));
    }

    @DeleteMapping("delete/{id}")
    @Transactional
    public ResponseEntity<Void> deleteRawMaterialById(@PathVariable("id") Integer id) {
        rawMaterialService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
