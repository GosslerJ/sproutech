package com.greenfoxacademy.springwebapp.config.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductsDTO {

  private List<ProductDTO> products;

  public ProductsDTO() {
    products = new ArrayList<>();
  }

}