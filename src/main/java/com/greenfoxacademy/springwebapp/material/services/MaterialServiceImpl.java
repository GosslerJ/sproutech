package com.greenfoxacademy.springwebapp.material.services;

import com.greenfoxacademy.springwebapp.material.models.Material;
import com.greenfoxacademy.springwebapp.material.models.MaterialRequestDTO;
import com.greenfoxacademy.springwebapp.material.models.MaterialResponseDTO;
import com.greenfoxacademy.springwebapp.material.repositories.MaterialRepository;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl implements MaterialService {

  private MaterialRepository materialRepository;

  public MaterialServiceImpl(MaterialRepository materialRepository) {
    this.materialRepository = materialRepository;
  }

  @Override
  public MaterialResponseDTO saveMaterial(MaterialRequestDTO dto) {
    Material material = Material.builder()
            .quality(dto.getQuality())
            .size(dto.getSize())
            .hitNumber(dto.getHitNumber())
            .quantity(dto.getQuantity())
            .unitPrice(dto.getUnitPrice())
            .unitLength(dto.getUnitLength())
            .unitWeight(dto.getUnitWeight())
            .warehouse(dto.getWarehouse())
            .build();
    material.setTotalLength(material.getUnitLength() * material.getQuantity());
    material.setTotalWeight(material.getUnitWeight() * material.getUnitLength() * material.getQuantity());
    material.setRemainingLength(material.getTotalLength());
    material.setRemainingWeight(material.getTotalWeight());
    material.setUpdatedAt(java.time.LocalDate.now());
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

}