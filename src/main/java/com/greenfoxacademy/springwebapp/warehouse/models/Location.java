package com.greenfoxacademy.springwebapp.warehouse.models;

public enum Location {
  EXTERNAL,
  INTERNAL,
  BAG,
  DELIVERED;

  public String toDbValue() {
    return this.name().toLowerCase();
  }

  public static Location fromDbValue(String dbValue) {
    return Location.valueOf(dbValue.toUpperCase());
  }

}
