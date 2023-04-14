package com.greenfoxacademy.springwebapp.product.models;

public enum ProductStatus {
  NEW,
  IN_PROGRESS,
  READY,
  DELIVERED;

  public String toDbValue() {
    return this.name().toLowerCase();
  }

  public static ProductStatus fromDbValue(String dbValue) {
    return ProductStatus.valueOf(dbValue.toUpperCase());
  }

}
