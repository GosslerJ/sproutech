package com.greenfoxacademy.springwebapp.material.services;

import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.material.models.Material;
import com.greenfoxacademy.springwebapp.material.models.MaterialRequestDTO;
import com.greenfoxacademy.springwebapp.material.models.MaterialResponseDTO;
import com.greenfoxacademy.springwebapp.material.repositories.MaterialRepository;
import com.greenfoxacademy.springwebapp.product.repositories.ProductRepository;
import com.greenfoxacademy.springwebapp.warehouse.models.Warehouse;
import com.greenfoxacademy.springwebapp.warehouse.repositories.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImpl implements MaterialService {

  private MaterialRepository materialRepository;
  private WarehouseRepository warehouseRepository;

  public MaterialServiceImpl(MaterialRepository materialRepository, WarehouseRepository warehouseRepository) {
    this.materialRepository = materialRepository;
    this.warehouseRepository = warehouseRepository;
  }

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
      Material remainingMaterial = materials.get(0);
      Double deltaLength = quantity * remainingMaterial.getUnitLength();
      Double deltaWeight = deltaLength * remainingMaterial.getUnitWeight();
      Double remainingLength = remainingMaterial.getRemainingLength() - deltaLength;
      Double remainingWeight = remainingMaterial.getRemainingWeight() - deltaWeight;
      remainingMaterial.setRemainingLength(remainingLength);
      remainingMaterial.setRemainingWeight(remainingWeight);
      remainingMaterial.setUpdatedAt(LocalDate.now());
      materialRepository.save(remainingMaterial);
      Warehouse internal = warehouseRepository.findById(2).orElseThrow(IdNotFoundException::new);
      Material transferredMaterial = Material.builder()
              .quality(remainingMaterial.getQuality())
              .size(remainingMaterial.getSize())
              .hitNumber(remainingMaterial.getHitNumber())
              .unitPrice(remainingMaterial.getUnitPrice())
              .unitWeight(remainingMaterial.getUnitWeight())
              .unitLength(remainingMaterial.getUnitLength())
              .totalWeight(deltaWeight)
              .totalLength(deltaLength)
              .remainingWeight(deltaWeight)
              .remainingLength(deltaLength)
              .updatedAt(LocalDate.now())
              .warehouse(internal)
              .build();
      materialRepository.save(transferredMaterial);
      return transferredMaterial;
    }

  }
}