package com.greenfoxacademy.springwebapp.config.services;

import com.greenfoxacademy.springwebapp.config.models.ProductCfDTO;
import com.greenfoxacademy.springwebapp.config.models.ProductsCfDTO;

import java.util.NoSuchElementException;

public interface ProductServiceCf {
  ProductsCfDTO findAll();

  ProductCfDTO findById(Integer id);

  ProductCfDTO save(ProductCfDTO productCfDTO);

  ProductCfDTO updateProduct(Integer id, ProductCfDTO productCfDTO) throws NoSuchElementException;

  void deleteProduct(Integer id) throws NoSuchElementException;
}
