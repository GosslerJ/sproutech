package com.greenfoxacademy.springwebapp.material.services;

import com.greenfoxacademy.springwebapp.material.models.Material;
import com.greenfoxacademy.springwebapp.material.models.MaterialRequestDTO;
import com.greenfoxacademy.springwebapp.material.models.MaterialResponseDTO;

import java.util.List;
import java.util.Optional;

public interface MaterialService {
  MaterialResponseDTO saveMaterial(MaterialRequestDTO materialRequestDTO);

  List<Material> findMaterial(Optional<String> quality, Optional<Double> size);

  Material transferMaterial(String quality, Double size, Integer quantity);

  Material assignMaterialToProduct(Integer productId, Integer materialId);
}
