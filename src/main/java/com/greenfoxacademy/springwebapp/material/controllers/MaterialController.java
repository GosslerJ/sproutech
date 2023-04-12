package com.greenfoxacademy.springwebapp.material.controllers;

import com.greenfoxacademy.springwebapp.material.models.Material;
import com.greenfoxacademy.springwebapp.material.models.MaterialRequestDTO;
import com.greenfoxacademy.springwebapp.material.models.MaterialResponseDTO;
import com.greenfoxacademy.springwebapp.material.services.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Raw material Registration")
@RestController
public class MaterialController {
  private MaterialService materialService;

  public MaterialController(MaterialService materialService) {
    this.materialService = materialService;
  }

  @Operation(summary = "Raw material Registration", description = "Add a new raw material")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = MaterialResponseDTO.class))),
  })
  @PostMapping("/material")
  public ResponseEntity<?> register(@Valid @RequestBody MaterialRequestDTO materialRequestDTO) {
    MaterialResponseDTO createdMaterial = materialService.saveMaterial(materialRequestDTO);
    return ResponseEntity.status(CREATED).body(createdMaterial);
  }

  @Operation(summary = "Raw material stock", description = "Get raw material by quality and size")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = MaterialResponseDTO.class))),
  })
  @GetMapping("/material")
  public ResponseEntity<?> filterMaterials(@RequestParam Optional<String> quality,
                                          @RequestParam Optional<Float> size) {
    List<Material> materials = materialService.findMaterial(quality, size);
    return ResponseEntity.status(OK).body(materials);
  }

}
