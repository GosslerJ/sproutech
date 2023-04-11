package com.greenfoxacademy.springwebapp.material.controllers;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

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
                          //          @ApiResponse(responseCode = "409", description = "name is already taken",
                          //                  content = @Content(mediaType = "application/json",
                          //                          schema = @Schema(implementation = StatusResponseDTO.class))),
                          //          TODO: scenarios?
  })
  @PostMapping("/material")
  public ResponseEntity<?> register(@Valid @RequestBody MaterialRequestDTO materialRequestDTO) {
    //    try {
    MaterialResponseDTO createdMaterial = materialService.saveMaterial(materialRequestDTO);
    return ResponseEntity.status(CREATED).body(createdMaterial);
    //        } catch (Exception e) {
    //        return ResponseEntity.status(CONFLICT).body(new StatusResponseDTO("error", e.getMessage()));
    //    }
  }

}
