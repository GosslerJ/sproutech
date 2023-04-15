package com.greenfoxacademy.springwebapp.warehouse.models;

import org.junit.jupiter.api.Test;

import static com.greenfoxacademy.springwebapp.warehouse.models.Location.BAG;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LocationConverterTest {

  private LocationConverter locationConverter = new LocationConverter();

  @Test
  void convertToDatabaseColumn_ConvertsLocationToDbValue() {
    Location location = BAG;
    String expectedDbValue = location.toDbValue();

    String actualDbValue = locationConverter.convertToDatabaseColumn(location);

    assertThat(actualDbValue).isEqualTo(expectedDbValue);
  }

  @Test
  void convertToEntityAttribute_ConvertsDbValueToLocation() {
    String dbValue = "bag";
    Location expectedLocation = BAG;

    Location actualLocation = locationConverter.convertToEntityAttribute(dbValue);

    assertThat(actualLocation).isEqualTo(expectedLocation);
  }

}
