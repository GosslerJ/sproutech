package com.greenfoxacademy.springwebapp.order.models;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

  @Override
  public String convertToDatabaseColumn(OrderStatus orderStatus) {
    return orderStatus.toDbValue();
  }

  @Override
  public OrderStatus convertToEntityAttribute(String dbValue) {
    return OrderStatus.fromDbValue(dbValue);
  }
}