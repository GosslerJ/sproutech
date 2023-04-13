package com.greenfoxacademy.springwebapp.product.services;

import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.material.models.Material;
import com.greenfoxacademy.springwebapp.material.repositories.MaterialRepository;
import com.greenfoxacademy.springwebapp.product.models.Product;
import com.greenfoxacademy.springwebapp.product.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

  private ProductRepository productRepository;
  private MaterialRepository materialRepository;

  public ProductServiceImpl(ProductRepository productRepository, MaterialRepository materialRepository) {
    this.productRepository = productRepository;
    this.materialRepository = materialRepository;
  }

  @Override
  public Material assignMaterialToProduct(Integer productId, Integer materialId) {
    Product product = productRepository.findById(productId).orElseThrow(IdNotFoundException::new);
    Material material = materialRepository.findById(materialId).orElseThrow(IdNotFoundException::new);
    Double assignedLength = product.getQuantity() * (product.getLength()/1000 + 0.001);
    Double assignedWeight = assignedLength * material.getUnitWeight();
    Double newLength = material.getRemainingLength() - assignedLength;
    Double newWeight = material.getRemainingWeight() - assignedWeight;
    material.setRemainingLength(assignedLength);
    material.setRemainingWeight(assignedWeight);
    material.setUpdatedAt(LocalDate.now());
    List<Material> materials = new ArrayList<>();
    materials.add(material);
    product.setMaterialProducts(materials);
    for (Material m : materials) {
      materialRepository.save(m);
    }
    Set<Material> materialss = new HashSet<>();
    materialss.add(material);
    Material newMaterial = Material.builder()
            .quality(material.getQuality())
            .size(material.getSize())
            .hitNumber(material.getHitNumber())
            .unitPrice(material.getUnitPrice())
            .unitWeight(material.getUnitWeight())
            .unitLength(material.getUnitLength())
            .totalWeight(newWeight)
            .totalLength(newLength)
            .remainingWeight(newWeight)
            .remainingLength(newLength)
            .updatedAt(LocalDate.now())
            .warehouse(material.getWarehouse())
            .build();
    materialRepository.save(newMaterial);
    productRepository.save(product);
    return newMaterial;
  }

}

