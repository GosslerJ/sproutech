package com.greenfoxacademy.springwebapp.config.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductsCfDTO {

  private List<ProductCfDTO> products;

  public ProductsCfDTO() {
    products = new ArrayList<>();
  }

}