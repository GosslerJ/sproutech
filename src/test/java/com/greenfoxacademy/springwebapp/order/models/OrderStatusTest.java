package com.greenfoxacademy.springwebapp.order.models;

import org.junit.jupiter.api.Test;

import static com.greenfoxacademy.springwebapp.order.models.OrderStatus.IN_PROGRESS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderStatusTest {
  @Test
  void toDbValue_ConvertsEnumNameToLowerCaseString() {
    OrderStatus orderStatus = IN_PROGRESS;
    String expectedDbValue = "in_progress";

    String actualDbValue = orderStatus.toDbValue();

    assertThat(actualDbValue).isEqualTo(expectedDbValue);
  }

}
