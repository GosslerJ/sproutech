package com.greenfoxacademy.springwebapp.material.controllers;

import com.greenfoxacademy.springwebapp.material.models.Material;
import com.greenfoxacademy.springwebapp.material.models.MaterialRequestDTO;
import com.greenfoxacademy.springwebapp.material.models.MaterialResponseDTO;
import com.greenfoxacademy.springwebapp.material.services.MaterialService;
import com.greenfoxacademy.springwebapp.product.models.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Raw material Registration")
@AllArgsConstructor
@RequestMapping("/api")
@RestController
public class MaterialController {
  private MaterialService materialService;

  @Operation(summary = "Raw material registration", description = "Add a new raw material")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = MaterialResponseDTO.class))),
  })
  @PostMapping("/material")
  public ResponseEntity<?> register(@RequestBody MaterialRequestDTO materialRequestDTO) {
    MaterialResponseDTO createdMaterial = materialService.saveMaterial(materialRequestDTO);
    return ResponseEntity.status(CREATED).body(createdMaterial);
  }

  @Operation(summary = "Raw material stock", description = "Get raw material by quality and size")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Material.class))),
  })
  @GetMapping("/material")
  public ResponseEntity<?> filterMaterials(@RequestParam Optional<String> quality,
                                           @RequestParam Optional<Double> size) {
    List<Material> materials = materialService.findMaterial(quality, size);
    return ResponseEntity.status(OK).body(materials);
  }

  @Operation(summary = "Transfer materials", description = "Transfer materials to the internal warehouse")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Product.class))),
  })
  @PutMapping("/material")
  public ResponseEntity<?> transferMaterial(@RequestParam String quality, @RequestParam Double size,
                                            @RequestParam Integer quantity) {
    Material transferedMaterial = materialService.transferMaterial(quality, size, quantity);
    return ResponseEntity.status(OK).body(transferedMaterial);
  }

}
