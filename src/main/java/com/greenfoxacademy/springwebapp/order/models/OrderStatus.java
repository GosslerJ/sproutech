package com.greenfoxacademy.springwebapp.order.models;

public enum OrderStatus {
  NEW,
  IN_PROGRESS,
  DONE,
  DELIVERED,
  CANCELLED;

  public String toDbValue() {
    return this.name().toLowerCase();
  }

  public static OrderStatus fromDbValue(String dbValue) {
    return OrderStatus.valueOf(dbValue.toUpperCase());
  }
}