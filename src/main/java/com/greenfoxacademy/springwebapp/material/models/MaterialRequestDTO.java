package com.greenfoxacademy.springwebapp.material.models;

import com.greenfoxacademy.springwebapp.warehouse.models.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialRequestDTO {
  @NotBlank(message = "Quality is required.")
  private String quality;
  private Float size;
  private Long hitNumber;
  private Integer quantity;
  private Long unitPrice;
  private Float unitLength;
  private Float unitWeight;
  private Float totalLength;
  private Float totalWeight;
  private Float remainingLength;
  private Float remainingWeight;
  private LocalDate updatedAt;
  private Warehouse warehouse;

}
