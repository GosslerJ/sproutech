package com.greenfoxacademy.springwebapp.order.models;

import org.junit.jupiter.api.Test;

import static com.greenfoxacademy.springwebapp.order.models.OrderStatus.NEW;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderStatusConverterTest {

  private OrderStatusConverter orderStatusConverter = new OrderStatusConverter();

  @Test
  void convertToDatabaseColumn_ConvertsOrderStatusToDbValue() {
    OrderStatus orderStatus = NEW;
    String expectedDbValue = orderStatus.toDbValue();

    String actualDbValue = orderStatusConverter.convertToDatabaseColumn(orderStatus);

    assertThat(actualDbValue).isEqualTo(expectedDbValue);
  }

  @Test
  void convertToEntityAttribute_ConvertsDbValueToOrderStatus() {
    String dbValue = "new";
    OrderStatus expectedOrderStatus = NEW;

    OrderStatus actualOrderStatus = orderStatusConverter.convertToEntityAttribute(dbValue);

    assertThat(actualOrderStatus).isEqualTo(expectedOrderStatus);
  }

}
