package com.greenfoxacademy.springwebapp.product.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductListDTO {
  private List<ProductRequestDTO> products;

  public ProductListDTO() {
    products = new ArrayList<>();
  }

  public ProductListDTO(List<ProductRequestDTO> products) {
    this.products = products;
  }
}
