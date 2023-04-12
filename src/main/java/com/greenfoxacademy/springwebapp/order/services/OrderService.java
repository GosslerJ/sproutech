package com.greenfoxacademy.springwebapp.order.services;

import com.greenfoxacademy.springwebapp.order.models.Order;
import com.greenfoxacademy.springwebapp.order.models.OrderRequestDTO;
import com.greenfoxacademy.springwebapp.order.models.OrderResponseDTO;

import java.util.List;

public interface OrderService {
  OrderResponseDTO saveOrder(OrderRequestDTO orderRequestDTO);

  void deleteOrderById(Integer id);

  List<Order> filterOrdersByDeliveryDeadline(Integer days);
}
