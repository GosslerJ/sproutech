package com.greenfoxacademy.springwebapp.product.models;

import com.greenfoxacademy.springwebapp.material.models.Material;
import com.greenfoxacademy.springwebapp.order.models.Order;
import lombok.*;

import javax.persistence.*;
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
  private Float size;
  private Float length;
  private Integer quantity;
  @ManyToMany(mappedBy = "orderedProducts")
  private List<Order> orderedProducts;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "material_product",
          joinColumns = @JoinColumn(name = "product_id"),
          inverseJoinColumns = @JoinColumn(name = "material_id")
  )
  private List<Material> materialProducts;
}
