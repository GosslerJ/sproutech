package com.greenfoxacademy.springwebapp.material.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.springwebapp.product.models.Product;
import lombok.*;
import com.greenfoxacademy.springwebapp.warehouse.models.Warehouse;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Builder // TODO: tests can be different see: https://www.baeldung.com/lombok-builder-default-value
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
  @JsonIgnore
  @ManyToMany(mappedBy = "materialProducts")
  private List<Product> frog;
  @ManyToOne(fetch = FetchType.EAGER)
  private Warehouse warehouse;

}
