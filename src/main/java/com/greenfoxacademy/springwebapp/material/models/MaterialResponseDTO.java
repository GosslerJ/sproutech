package com.greenfoxacademy.springwebapp.material.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialResponseDTO {

  private Integer id;
  private String quality;
  private Double size;
  private Long hitNumber;
  private Integer quantity;
  private Double unitLength;
  private Double unitWeight;
  private Double totalLength;
  private Double totalWeight;

}
