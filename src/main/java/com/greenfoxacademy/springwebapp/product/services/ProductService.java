package com.greenfoxacademy.springwebapp.product.services;

import com.greenfoxacademy.springwebapp.material.models.Material;
import com.greenfoxacademy.springwebapp.product.models.Product;

public interface ProductService {
  Material assignMaterialToProduct(Integer productId, Integer materialId);
}
