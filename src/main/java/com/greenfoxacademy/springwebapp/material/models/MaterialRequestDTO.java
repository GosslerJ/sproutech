package com.greenfoxacademy.springwebapp.material.models;

import com.greenfoxacademy.springwebapp.warehouse.models.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialRequestDTO {
  @NotBlank(message = "Quality is required.")
  private String quality;
  private Double size;
  private Long hitNumber;
  private Long unitPrice;
  private Double unitWeight;
  private Double unitLength;
  private Double totalWeight;
  private Warehouse warehouse;

  public Double getTotalLength() {
    return totalWeight * unitLength;
  }

}
