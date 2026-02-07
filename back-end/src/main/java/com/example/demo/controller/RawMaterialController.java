package com.example.demo.controller;

import com.example.demo.dto.RawMaterialDto.DetailsRawMaterialDto;
import com.example.demo.dto.RawMaterialDto.RegisterRawMaterialDto;
import com.example.demo.dto.RawMaterialDto.UpdateRawMaterialDto;
import com.example.demo.service.RawMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("rawMaterial")
@Tag(name = "Raw Material Controller", description = "API of Raw Material Class, should register a new raw material in the database," +
        "search by ID, update by ID, delete by ID and list registered raw materials.")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @PostMapping("register")
    @Transactional
    @Operation(summary = "Register Raw Material", description = "Should register raw material in database.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Raw Material successfully registered.",
                    content = @Content(schema = @Schema(implementation = DetailsRawMaterialDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request in JSON format."),
            @ApiResponse(responseCode = "500", description = "Server error.")
    })
    public ResponseEntity<DetailsRawMaterialDto> registerRawMaterial(@RequestBody @Validated RegisterRawMaterialDto rawMaterialDto,
                                                                     UriComponentsBuilder uriBuilder) {
        var saved = rawMaterialService.saveRawMaterial(rawMaterialDto);
        var uri = uriBuilder.path("rawMaterial/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailsRawMaterialDto(saved));
    }

    @GetMapping("findById/{id}")
    @Operation(summary = "Details Raw Material", description = "Should find raw material by id and then details is returned.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Raw Material successfully returned.",
                    content = @Content(schema = @Schema(implementation = DetailsRawMaterialDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Raw Material ID Not Found."),
            @ApiResponse(responseCode = "500", description = "Server error.")
    })
    public ResponseEntity<DetailsRawMaterialDto> findByIdDetailsRawMaterial(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(rawMaterialService.findById(id));
    }

    @GetMapping("listRawMaterials")
    @Operation(summary = "List Raw Material", description = "Should return list raw materials.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List Raw Material successfully returned.",
                    content = @Content(schema = @Schema(implementation = DetailsRawMaterialDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "List Raw Material Not Found (empty)."),
            @ApiResponse(responseCode = "500", description = "ServerError.")
    })
    public ResponseEntity<List<DetailsRawMaterialDto>> listDetailsRawMaterials(Pageable pageable) {
        var list = rawMaterialService.findAll(pageable);
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    @PutMapping("updateRawMaterial/{id}")
    @Transactional
    @Operation(summary = "Update Raw Material", description = "Should update raw material by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Raw Material successfully updated.",
                    content = @Content(schema = @Schema(implementation = DetailsRawMaterialDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request in JSON format."),
            @ApiResponse(responseCode = "404", description = "Raw Material ID Not Found."),
            @ApiResponse(responseCode = "500", description = "Server error.")
    })
    public ResponseEntity<DetailsRawMaterialDto> updateRawMaterial(@PathVariable("id") Integer id,
                                                                   @RequestBody @Validated UpdateRawMaterialDto rawMaterialDto) {
        return ResponseEntity.ok().body(rawMaterialService.updateRawMaterial(id, rawMaterialDto));
    }

    @DeleteMapping("delete/{id}")
    @Transactional
    @Operation(summary = "Delete Raw Material", description = "Should delete raw material by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Raw Material successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Raw Material ID Not Found."),
            @ApiResponse(responseCode = "500", description = "Server Error.")
    })
    public ResponseEntity<Void> deleteRawMaterialById(@PathVariable("id") Integer id) {
        rawMaterialService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
