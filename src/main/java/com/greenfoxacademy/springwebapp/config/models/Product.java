package com.greenfoxacademy.springwebapp.config.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

  @Column(name = "product_code")
  private String code;

  @Column(name = "product_version_from")
  private Integer productVersionFrom;

  @Column(name = "product_version_to")
  private Integer productVersionTo;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
  private List<Package> packages;

}