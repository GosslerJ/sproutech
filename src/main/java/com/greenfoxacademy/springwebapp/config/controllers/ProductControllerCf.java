package com.greenfoxacademy.springwebapp.config.controllers;

import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.common.models.ErrorDTO;
import com.greenfoxacademy.springwebapp.common.models.StatusResponseDTO;
import com.greenfoxacademy.springwebapp.config.models.ProductCfDTO;
import com.greenfoxacademy.springwebapp.config.models.ProductsCfDTO;
import com.greenfoxacademy.springwebapp.config.services.ProductServiceCf;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Product", description = "Product related endpoints")
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductControllerCf {

  private final ProductServiceCf productServiceCf;

  @Operation(summary = "Get all products")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ProductsCfDTO.class))),
  })
  @GetMapping("/products")
  public ResponseEntity<ProductsCfDTO> findAllProducts() {
    ProductsCfDTO productsCfDTO = productServiceCf.findAll();
    return ResponseEntity.ok(productsCfDTO);
  }

  @Operation(summary = "Get products by id", description = "List packages by product by id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ProductCfDTO.class))),
          @ApiResponse(responseCode = "404", description = "id not found",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ErrorDTO.class))),
  })
  @GetMapping("/products/{id}")
  public ResponseEntity<ProductCfDTO> findById(@PathVariable Integer id) {
    ProductCfDTO productCfDTO = productServiceCf.findById(id);
    return ResponseEntity.ok(productCfDTO);
  }

  @Operation(summary = "Post new product", description = "Save new product to the products table")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ProductCfDTO.class))),
  })
  @PostMapping("/products")
  public ResponseEntity<ProductCfDTO> saveProduct(@Valid @RequestBody ProductCfDTO productCfDTO) {
    ProductCfDTO savedProductCfDTO = productServiceCf.save(productCfDTO);
    return ResponseEntity.status(201).body(savedProductCfDTO);
  }

  @Operation(summary = "Update product", description = "Update product by id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ProductCfDTO.class))),
  })
  @PutMapping("/products/{id}")
  public ResponseEntity<ProductCfDTO> updateProduct(@PathVariable Integer id, @Valid @RequestBody ProductCfDTO productCfDTO) {
    return ResponseEntity.status(201).body(productServiceCf.updateProduct(id, productCfDTO));
  }


  @Operation(summary = "Delete product", description = "Delete an existing product by id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = StatusResponseDTO.class))),
          @ApiResponse(responseCode = "404", description = "id not found",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ErrorDTO.class))),
  })
  @DeleteMapping("/products/{id}")
  public ResponseEntity<?> deleteProduct(@PathVariable Integer id) throws IdNotFoundException {
    productServiceCf.deleteProduct(id);
    return ResponseEntity.status(OK).body(new StatusResponseDTO("ok", "Product deleted"));
  }

}
