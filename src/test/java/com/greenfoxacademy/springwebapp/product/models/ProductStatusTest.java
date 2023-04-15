package com.greenfoxacademy.springwebapp.product.models;

import org.junit.jupiter.api.Test;

import static com.greenfoxacademy.springwebapp.product.models.ProductStatus.READY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductStatusTest {

  @Test
  void toDbValue_ConvertsEnumNameToLowerCaseString() {
    ProductStatus productStatus = READY;
    String expectedDbValue = "ready";

    String actualDbValue = productStatus.toDbValue();

    assertThat(actualDbValue).isEqualTo(expectedDbValue);
  }

}
