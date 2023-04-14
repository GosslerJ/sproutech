package com.greenfoxacademy.springwebapp.material.services;

import com.greenfoxacademy.springwebapp.common.exceptions.AlreadyProducedException;
import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.common.exceptions.NotEnoughMaterialException;
import com.greenfoxacademy.springwebapp.common.exceptions.QualityDifferenceException;
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

import static com.greenfoxacademy.springwebapp.product.models.ProductStatus.DELIVERED;
import static com.greenfoxacademy.springwebapp.product.models.ProductStatus.READY;

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
            .originalWeight(dto.getOriginalWeight())
            .originalLength(dto.getOriginalLength())
            .remainingWeight(dto.getOriginalWeight())
            .remainingLength(dto.getOriginalLength())
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
    responseDTO.setOriginalLength(material.getOriginalLength());
    responseDTO.setOriginalWeight(material.getOriginalWeight());
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
    materials.sort(Comparator.comparing(Material::getOriginalLength));
    if (materials.size() == 0) return new Material();
    Material material = materials.get(0);
    Double deltaLength = quantity * material.getUnitLength();
    if (deltaLength <= material.getOriginalLength()) {
      Double deltaWeight = deltaLength * material.getUnitWeight();
      Double remainingLength = material.getRemainingLength() - deltaLength;
      Double remainingWeight = material.getRemainingWeight() - deltaWeight;
      updateMaterial(material, quantity, remainingLength, remainingWeight);
      Material transferredMaterial = buildMaterial(material, deltaLength, deltaWeight);
      materialRepository.save(transferredMaterial);
      return transferredMaterial;
    } else {
      throw new NotEnoughMaterialException();
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
    Double assignedLength = requestValidation(product, material);
    Double assignedWeight = assignedLength * material.getUnitWeight();
    material.setRemainingLength(assignedLength);
    material.setRemainingWeight(assignedWeight);
    material.setUpdatedAt(LocalDate.now());
    List<Material> materials = new ArrayList<>();
    materials.add(material);
    product.setMaterialProducts(materials);
    product.setStatus(READY);
    for (Material m : materials) {
      materialRepository.save(m);
    }
    productRepository.save(product);
    return addRemainingMaterial(material, assignedLength, assignedWeight);
  }

  public Double requestValidation(Product product, Material material) {
    if (product.getStatus() == READY || product.getStatus() == DELIVERED) {
      throw new AlreadyProducedException();
    }
    if (product.getQuality() != material.getQuality()) {
      throw new QualityDifferenceException();
    }
    Double assignedLength = product.getQuantity() * (product.getLength() / 1000 + 0.001);
    if (assignedLength > material.getRemainingLength()) {
      throw new NotEnoughMaterialException();
    }
    return assignedLength;
  }

  @Override
  public Material addRemainingMaterial(Material material, Double assignedLength, Double assignedWeight) {
    Double newLength = material.getOriginalLength() - assignedLength;
    Double newWeight = material.getOriginalWeight() - assignedWeight;
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
            .originalWeight(deltaWeight)
            .originalLength(deltaLength)
            .remainingWeight(deltaWeight)
            .remainingLength(deltaLength)
            .updatedAt(LocalDate.now())
            .warehouse(internal)
            .build();
  }

}
