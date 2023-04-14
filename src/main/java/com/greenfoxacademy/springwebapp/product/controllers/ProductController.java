package com.greenfoxacademy.springwebapp.product.controllers;

import com.greenfoxacademy.springwebapp.common.models.ErrorDTO;
import com.greenfoxacademy.springwebapp.material.models.Material;
import com.greenfoxacademy.springwebapp.material.services.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Product", description = "Product related endpoints")
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductController {

  private MaterialService materialService;

  @Operation(summary = "Organize production", description = "Put material into production")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Material.class))),
          @ApiResponse(responseCode = "404", description = "id not found",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ErrorDTO.class))),
          @ApiResponse(responseCode = "406", description = "invalid query parameter",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ErrorDTO.class))),
  })
  @PutMapping("/production")
  public ResponseEntity<?> production(@RequestParam Integer productId, @RequestParam Integer materialId) {
    Material material = materialService.assignMaterialToProduct(productId, materialId);
    return ResponseEntity.status(OK).body(material);
  }

}
