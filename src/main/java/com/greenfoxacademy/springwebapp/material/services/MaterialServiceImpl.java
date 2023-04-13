package com.greenfoxacademy.springwebapp.material.services;

import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.material.models.Material;
import com.greenfoxacademy.springwebapp.material.models.MaterialRequestDTO;
import com.greenfoxacademy.springwebapp.material.models.MaterialResponseDTO;
import com.greenfoxacademy.springwebapp.material.repositories.MaterialRepository;
import com.greenfoxacademy.springwebapp.product.models.Product;
import com.greenfoxacademy.springwebapp.product.repositories.ProductRepository;
import com.greenfoxacademy.springwebapp.warehouse.models.Warehouse;
import com.greenfoxacademy.springwebapp.warehouse.repositories.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MaterialServiceImpl implements MaterialService {

  private ProductRepository productRepository;
  private MaterialRepository materialRepository;
  private WarehouseRepository warehouseRepository;

  @Override
  public MaterialResponseDTO saveMaterial(MaterialRequestDTO dto) {
    Warehouse external = warehouseRepository.findById(1).orElseThrow(IdNotFoundException::new);
    Material material = Material.builder()
            .quality(dto.getQuality())
            .size(dto.getSize())
            .hitNumber(dto.getHitNumber())
            .unitPrice(dto.getUnitPrice())
            .unitWeight(dto.getUnitWeight())
            .unitLength(dto.getUnitLength())
            .totalWeight(dto.getTotalWeight())
            .totalLength(dto.getTotalLength())
            .remainingWeight(dto.getTotalWeight())
            .remainingLength(dto.getTotalLength())
            .updatedAt(LocalDate.now())
            .warehouse(external)
            .build();
    materialRepository.save(material);
    return convert(material);
  }

  @Override
  public MaterialResponseDTO convert(Material material) {
    if (material == null) return null;
    MaterialResponseDTO responseDTO = new MaterialResponseDTO();
    responseDTO.setId(material.getId());
    responseDTO.setQuality(material.getQuality());
    responseDTO.setSize(material.getSize());
    responseDTO.setHitNumber(material.getHitNumber());
    responseDTO.setQuantity(material.getQuantity());
    responseDTO.setUnitLength(material.getUnitLength());
    responseDTO.setUnitWeight(material.getUnitWeight());
    responseDTO.setTotalLength(material.getTotalLength());
    responseDTO.setTotalWeight(material.getTotalWeight());
    return responseDTO;
  }

  @Override
  public List<Material> findMaterial(Optional<String> quality, Optional<Double> size) {
    if (quality.isPresent() && size.isPresent()) {
      return materialRepository.findAllByQualityAndSize(quality.get(), size.get());
    } else if (quality.isPresent()) {
      return materialRepository.findAllByQuality(quality.get());
    } else if (size.isPresent()) {
      return materialRepository.findAllBySize(size.get());
    }
    return new ArrayList<>();
  }

  @Override
  public Material transferMaterial(String quality, Double size, Integer quantity) {
    List<Material> materials = materialRepository.findAllByQualityAndSizeAndWarehouseId(quality, size, 1);
    materials.sort(Comparator.comparing(Material::getTotalLength));
    if (materials.size() == 0) {
      return new Material();
    } else {
      Material material = materials.get(0);
      Double deltaLength = quantity * material.getUnitLength();
      Double deltaWeight = deltaLength * material.getUnitWeight();
      Double remainingLength = material.getRemainingLength() - deltaLength;
      Double remainingWeight = material.getRemainingWeight() - deltaWeight;
      updateMaterial(material, quantity, remainingLength, remainingWeight);
      Material transferredMaterial = buildMaterial(material, deltaLength, deltaWeight);
      materialRepository.save(transferredMaterial);
      return transferredMaterial;
    }
  }

  @Override
  public void updateMaterial(Material remainingMaterial,
                              Integer quantity, Double remainingLength, Double remainingWeight) {
    remainingMaterial.setRemainingLength(remainingLength);
    remainingMaterial.setRemainingWeight(remainingWeight);
    remainingMaterial.setUpdatedAt(LocalDate.now());
    materialRepository.save(remainingMaterial);
  }

  @Override
  public Material assignMaterialToProduct(Integer productId, Integer materialId) {
    Product product = productRepository.findById(productId).orElseThrow(IdNotFoundException::new);
    Material material = materialRepository.findById(materialId).orElseThrow(IdNotFoundException::new);
    Double assignedLength = product.getQuantity() * (product.getLength() / 1000 + 0.001);
    Double assignedWeight = assignedLength * material.getUnitWeight();
    material.setRemainingLength(assignedLength);
    material.setRemainingWeight(assignedWeight);
    material.setUpdatedAt(LocalDate.now());
    List<Material> materials = new ArrayList<>();
    materials.add(material);
    product.setMaterialProducts(materials);
    for (Material m : materials) {
      materialRepository.save(m);
    }
    productRepository.save(product);
    return addRemainingMaterial(material, assignedLength, assignedWeight);
  }

  @Override
  public Material addRemainingMaterial(Material material, Double assignedLength, Double assignedWeight) {
    Double newLength = material.getRemainingLength() - assignedLength;
    Double newWeight = material.getRemainingWeight() - assignedWeight;
    Material newMaterial = buildMaterial(material, newLength, newWeight);
    return materialRepository.save(newMaterial);
  }

  @Override
  public Material buildMaterial(Material material, Double deltaLength, Double deltaWeight) {
    Warehouse internal = warehouseRepository.findById(2).orElseThrow(IdNotFoundException::new);
    return Material.builder()
            .quality(material.getQuality())
            .size(material.getSize())
            .hitNumber(material.getHitNumber())
            .unitPrice(material.getUnitPrice())
            .unitWeight(material.getUnitWeight())
            .unitLength(material.getUnitLength())
            .totalWeight(deltaWeight)
            .totalLength(deltaLength)
            .remainingWeight(deltaWeight)
            .remainingLength(deltaLength)
            .updatedAt(LocalDate.now())
            .warehouse(internal)
            .build();
  }

}
