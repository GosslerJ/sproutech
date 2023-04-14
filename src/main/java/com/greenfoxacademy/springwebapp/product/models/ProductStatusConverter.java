package com.greenfoxacademy.springwebapp.product.models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProductStatusConverter implements AttributeConverter<ProductStatus, String> {

  @Override
  public String convertToDatabaseColumn(ProductStatus productStatus) {
    return productStatus.toDbValue();
  }

  @Override
  public ProductStatus convertToEntityAttribute(String dbValue) {
    return ProductStatus.fromDbValue(dbValue);
  }

}
