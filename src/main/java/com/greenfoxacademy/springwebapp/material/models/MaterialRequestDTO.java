package com.greenfoxacademy.springwebapp.material.models;

import com.greenfoxacademy.springwebapp.warehouse.models.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
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
  private Double originalWeight;
  private Warehouse warehouse;

  public Double getOriginalLength() {
    return originalWeight * unitLength;
  }

}
