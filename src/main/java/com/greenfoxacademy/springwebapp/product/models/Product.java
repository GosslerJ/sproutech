package com.greenfoxacademy.springwebapp.product.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.springwebapp.material.models.Material;
import com.greenfoxacademy.springwebapp.order.models.Order;
import com.greenfoxacademy.springwebapp.order.models.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String quality;
  private Double size;
  private Double length;
  private Integer quantity;
  @JsonIgnore
  private LocalDate deliveryDate;
  @JsonIgnore
  private Long deliveryNumber;
  @JsonIgnore
  @ManyToMany(mappedBy = "orderedProducts")
  private List<Order> orderedProducts;
  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "material_product",
          joinColumns = @JoinColumn(name = "product_id"),
          inverseJoinColumns = @JoinColumn(name = "material_id")
  )
  private List<Material> materialProducts;
}
