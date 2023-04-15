package com.greenfoxacademy.springwebapp.warehouse.models;

import org.junit.jupiter.api.Test;

import static com.greenfoxacademy.springwebapp.warehouse.models.Location.BAG;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LocationTest {

  @Test
  void toDbValue_ConvertsEnumNameToLowerCaseString() {
    Location location = BAG;
    String expectedDbValue = "bag";

    String actualDbValue = location.toDbValue();

    assertThat(actualDbValue).isEqualTo(expectedDbValue);
  }
}
