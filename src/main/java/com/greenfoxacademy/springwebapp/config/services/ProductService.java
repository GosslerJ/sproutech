package com.greenfoxacademy.springwebapp.config.services;

import com.greenfoxacademy.springwebapp.config.models.ProductDTO;
import com.greenfoxacademy.springwebapp.config.models.ProductsDTO;

import java.util.NoSuchElementException;

public interface ProductService {
  ProductsDTO findAll();

  ProductDTO findById(Long id);

  ProductDTO save(ProductDTO productDTO);

  ProductDTO updateProduct(Long id, ProductDTO productDTO) throws NoSuchElementException;

  void deleteProduct(Long id) throws NoSuchElementException;
}
