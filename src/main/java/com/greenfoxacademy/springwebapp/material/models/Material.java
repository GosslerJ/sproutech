package com.greenfoxacademy.springwebapp.material.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.springwebapp.product.models.Product;
import lombok.*;
import com.greenfoxacademy.springwebapp.warehouse.models.Warehouse;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "materials")
public class Material {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String quality;
  private Double size; // mm
  private Long hitNumber;
  private Long unitPrice; // HUF/kg
  private Double unitWeight; // kg/m
  private Double unitLength; // m
  private Double originalWeight; // kg
  private Double remainingWeight; // kg
  private Double originalLength; // m
  private Double remainingLength; // m
  private LocalDate updatedAt;
  @JsonIgnore
  @ManyToMany(mappedBy = "materialProducts")
  private List<Product> frog;
  @ManyToOne(fetch = FetchType.EAGER)
  private Warehouse warehouse;

  public Integer getQuantity() {
    return (int) Math.floor(originalWeight / (unitWeight * unitLength));
  }

}
