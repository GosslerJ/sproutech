package com.greenfoxacademy.springwebapp.warehouse.models;

public enum Location {
  EXTERNAL,
  INTERNAL,
  BAG;

  public String toDbValue() {
    return this.name().toLowerCase();
  }

  public static Location fromDbValue(String dbValue) {
    return Location.valueOf(dbValue.toUpperCase());
  }

}
