package com.greenfoxacademy.springwebapp.order.models;

import com.greenfoxacademy.springwebapp.product.models.Product;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class OrderRequestDTO {
  private Integer customerId;
  private LocalDate orderDate;
  private LocalDate deliveryDeadline;
  private List<Product> orderedProducts;

}