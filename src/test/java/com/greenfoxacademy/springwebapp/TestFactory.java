package com.greenfoxacademy.springwebapp;

import com.greenfoxacademy.springwebapp.material.models.Material;
import com.greenfoxacademy.springwebapp.material.models.MaterialRequestDTO;
import com.greenfoxacademy.springwebapp.warehouse.models.Warehouse;

import static com.greenfoxacademy.springwebapp.warehouse.models.Location.EXTERNAL;

public class TestFactory {

  public static final Material materialBuilder() {
    return Material.builder()
            .id(1)
            .quality("quality")
            .size(20.0)
            .hitNumber(5L)
            .unitLength(10.0)
            .unitWeight(5.0)
            .originalLength(500.0)
            .originalWeight(250.0)
            .build();
  }

  public static final MaterialRequestDTO materialRequestDtoBuilder() {
    return MaterialRequestDTO.builder()
            .quality("quality")
            .size(20.0)
            .hitNumber(10L)
            .unitLength(10.0)
            .unitPrice(20L)
            .unitWeight(2.5)
            .unitLength(3.0)
            .originalWeight(25.0)
            .build();
  }

  public static final Warehouse warehouseBuilder() {
    return Warehouse.builder()
            .id(1)
            .name(EXTERNAL)
            .address("123 Main St")
            .city("Anytown")
            .zipCode("12345")
            .build();
  }

}
