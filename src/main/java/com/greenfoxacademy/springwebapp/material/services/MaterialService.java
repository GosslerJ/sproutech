package com.greenfoxacademy.springwebapp.material.services;

import com.greenfoxacademy.springwebapp.material.models.Material;
import com.greenfoxacademy.springwebapp.material.models.MaterialRequestDTO;
import com.greenfoxacademy.springwebapp.material.models.MaterialResponseDTO;
import com.greenfoxacademy.springwebapp.product.models.Product;

import java.util.List;
import java.util.Optional;

public interface MaterialService {
  MaterialResponseDTO saveMaterial(MaterialRequestDTO materialRequestDTO);

  MaterialResponseDTO convert(Material material);

  List<Material> findMaterial(Optional<String> quality, Optional<Double> size);

  Material transferMaterial(String quality, Double size, Integer quantity);

  void updateMaterial(Material remainingMaterial, Integer quantity, Double remainingLength, Double remainingWeight);

  Material assignMaterialToProduct(Integer productId, Integer materialId);

  Double requestValidation(Product product, Material material);

  Material addRemainingMaterial(Material material, Double assignedLength, Double assignedWeight);

  Material buildMaterial(Material material, Double deltaLength, Double deltaWeight);

}
