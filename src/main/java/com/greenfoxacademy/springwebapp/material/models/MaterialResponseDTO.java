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
  private Float size;
  private Long hitNumber;
  private Integer quantity;
  private Float unitLength;
  private Float unitWeight;
  private Float totalLength;
  private Float totalWeight;

}
