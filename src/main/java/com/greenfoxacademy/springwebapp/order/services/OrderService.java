package com.greenfoxacademy.springwebapp.order.services;

import com.greenfoxacademy.springwebapp.order.models.OrderRequestDTO;
import com.greenfoxacademy.springwebapp.order.models.OrderResponseDTO;

public interface OrderService {
  OrderResponseDTO saveOrder(OrderRequestDTO orderRequestDTO);

  void deleteOrderById(Integer id);
}
