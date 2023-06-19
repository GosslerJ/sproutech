package com.greenfoxacademy.springwebapp.config.controllers;

import com.greenfoxacademy.springwebapp.config.models.ProductDTO;
import com.greenfoxacademy.springwebapp.config.models.ProductsDTO;
import com.greenfoxacademy.springwebapp.config.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductController {

  private final ProductService productService;

  @GetMapping("/products")
  public ResponseEntity<ProductsDTO> findAllProducts() {
    ProductsDTO productsDTO = productService.findAll();
    return ResponseEntity.ok(productsDTO);
  }

  @GetMapping("/products/{id}")
  public ResponseEntity<ProductDTO> findById(@PathVariable Integer id) {
    ProductDTO productDTO = productService.findById(id);
    return ResponseEntity.ok(productDTO);
  }

  @PostMapping("/products")
  public ResponseEntity<ProductDTO> saveProduct(@Valid @RequestBody ProductDTO productDTO) {
    ProductDTO savedProductDTO = productService.save(productDTO);
    return ResponseEntity.status(201).body(savedProductDTO);
  }

  @PutMapping("/products/{id}")
  public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer id, @Valid @RequestBody ProductDTO productDTO) {
    return ResponseEntity.status(201).body(productService.updateProduct(id, productDTO));
  }

  @DeleteMapping("/products/{id}")
  public ResponseEntity deleteProduct(@PathVariable Integer id) {
    productService.deleteProduct(id);
    return ResponseEntity.status(204).build();
  }

}
