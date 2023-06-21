package com.greenfoxacademy.springwebapp.config.controllers;

import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.common.models.ErrorDTO;
import com.greenfoxacademy.springwebapp.common.models.StatusResponseDTO;
import com.greenfoxacademy.springwebapp.config.models.ProductDTO;
import com.greenfoxacademy.springwebapp.config.models.ProductsDTO;
import com.greenfoxacademy.springwebapp.config.services.ProductService;
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
public class ProductController {

  private final ProductService productService;

  @Operation(summary = "Get all products")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ProductsDTO.class))),
  })
  @GetMapping("/products")
  public ResponseEntity<ProductsDTO> findAllProducts() {
    ProductsDTO productsDTO = productService.findAll();
    return ResponseEntity.ok(productsDTO);
  }

  @Operation(summary = "Get products by id", description = "List packages by product by id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ProductDTO.class))),
          @ApiResponse(responseCode = "404", description = "id not found",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ErrorDTO.class))),
  })
  @GetMapping("/products/{id}")
  public ResponseEntity<ProductDTO> findById(@PathVariable Integer id) {
    ProductDTO productDTO = productService.findById(id);
    return ResponseEntity.ok(productDTO);
  }

  @Operation(summary = "Post new product", description = "Save new product to the products table")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ProductDTO.class))),
  })
  @PostMapping("/products")
  public ResponseEntity<ProductDTO> saveProduct(@Valid @RequestBody ProductDTO productDTO) {
    ProductDTO savedProductDTO = productService.save(productDTO);
    return ResponseEntity.status(201).body(savedProductDTO);
  }

  @Operation(summary = "Update product", description = "Update product by id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ProductDTO.class))),
  })
  @PutMapping("/products/{id}")
  public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer id, @Valid @RequestBody ProductDTO productDTO) {
    return ResponseEntity.status(201).body(productService.updateProduct(id, productDTO));
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
    productService.deleteProduct(id);
    return ResponseEntity.status(OK).body(new StatusResponseDTO("ok", "Product deleted"));
  }

}
