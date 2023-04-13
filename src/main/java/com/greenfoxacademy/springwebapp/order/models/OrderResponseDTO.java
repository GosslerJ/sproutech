package com.greenfoxacademy.springwebapp.order.models;

import com.greenfoxacademy.springwebapp.product.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
  private Integer id;
  private Integer customerId;
  private List<Product> orderedProducts;

}
