package com.greenfoxacademy.springwebapp.product.models;

import org.junit.jupiter.api.Test;

import static com.greenfoxacademy.springwebapp.product.models.ProductStatus.READY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductStatusConverterTest {

  private ProductStatusConverter productStatusConverter = new ProductStatusConverter();

  @Test
  void convertToDatabaseColumn_ConvertsProductStatusToDbValue() {
    ProductStatus productStatus = READY;
    String expectedDbValue = productStatus.toDbValue();

    String actualDbValue = productStatusConverter.convertToDatabaseColumn(productStatus);

    assertThat(actualDbValue).isEqualTo(expectedDbValue);
  }

  @Test
  void convertToEntityAttribute_ConvertsDbValueToProductStatus() {
    String dbValue = "ready";
    ProductStatus expectedProductStatus = READY;

    ProductStatus actualProductStatus = productStatusConverter.convertToEntityAttribute(dbValue);

    assertThat(actualProductStatus).isEqualTo(expectedProductStatus);
  }

}
