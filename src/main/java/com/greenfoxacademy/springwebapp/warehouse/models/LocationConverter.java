package com.greenfoxacademy.springwebapp.warehouse.models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocationConverter implements AttributeConverter<Location, String> {

  @Override
  public String convertToDatabaseColumn(Location location) {
    return location.toDbValue();
  }

  @Override
  public Location convertToEntityAttribute(String dbValue) {
    return Location.fromDbValue(dbValue);
  }

}
