package com.greenfoxacademy.springwebapp.config.services;

import com.greenfoxacademy.springwebapp.config.models.ProductDTO;
import com.greenfoxacademy.springwebapp.config.models.ProductsDTO;

import java.util.NoSuchElementException;

public interface ProductService {
  ProductsDTO findAll();

  ProductDTO findById(Integer id);

  ProductDTO save(ProductDTO productDTO);

  ProductDTO updateProduct(Integer id, ProductDTO productDTO) throws NoSuchElementException;

  void deleteProduct(Integer id) throws NoSuchElementException;
}
