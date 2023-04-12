package com.greenfoxacademy.springwebapp.warehouse.models;

import com.greenfoxacademy.springwebapp.material.models.Material;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "warehouses")
public class Warehouse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(columnDefinition = "enum('external', 'internal', 'bag')")
  private Location name;
  private String zipCode;
  private String city;
  private String address;
  @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
  private List<Material> materialList;

  public void setLocation(Location location) {
    this.name = location;
  }
}

  //@OneToMany(mappedBy = "kingdom", cascade = CascadeType.ALL)
  //  private List<Building> buildingList;
  //@ManyToOne(fetch = FetchType.EAGER)
  //  private Kingdom kingdom;