package com.greenfoxacademy.springwebapp;

import com.greenfoxacademy.springwebapp.material.models.Material;
import com.greenfoxacademy.springwebapp.material.models.MaterialRequestDTO;
import com.greenfoxacademy.springwebapp.product.models.Product;
import com.greenfoxacademy.springwebapp.warehouse.models.Warehouse;

import static com.greenfoxacademy.springwebapp.warehouse.models.Location.EXTERNAL;
import static com.greenfoxacademy.springwebapp.warehouse.models.Location.INTERNAL;

public class TestFactory {
  public static String QUALITY = "quality";
  public static double SIZE = 20.0;
  public static int QUANTITY = 3;
  public static double UNIT_LENGTH = 2.0;
  public static double UNIT_WEIGHT = 1.0;
  public static double ORIGINAL_LENGTH = 6.0;

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

  public static final Material transferMaterialBuilder() {
    double originalWeight = ORIGINAL_LENGTH * UNIT_WEIGHT;
    double remainingLength = ORIGINAL_LENGTH - (QUANTITY * UNIT_LENGTH);
    double remainingWeight = remainingLength * UNIT_WEIGHT;
    return Material.builder()
            .quality(QUALITY)
            .size(SIZE)
            .hitNumber(1L)
            .unitLength(UNIT_LENGTH)
            .unitWeight(UNIT_WEIGHT)
            .originalLength(ORIGINAL_LENGTH)
            .originalWeight(originalWeight)
            .remainingLength(remainingLength)
            .remainingWeight(remainingWeight)
            .warehouse(externalWarehouseBuilder())
            .build();
  }

  public static final Product productBuilder() {
    return Product.builder()
            .id(1)
            .name("Product 1")
            .quantity(1)
            .length(10.0)
            .quality("quality")
            .build();
  }

  public static final Warehouse externalWarehouseBuilder() {
    return Warehouse.builder()
            .id(1)
            .name(EXTERNAL)
            .address("123 Main St")
            .city("Anytown")
            .zipCode("12345")
            .build();
  }

  public static final Warehouse internalWarehouseBuilder() {
    return Warehouse.builder()
            .id(1)
            .name(INTERNAL)
            .address("123 Main St")
            .city("Anytown")
            .zipCode("12345")
            .build();
  }

}
