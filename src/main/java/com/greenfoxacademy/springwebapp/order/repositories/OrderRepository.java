package com.greenfoxacademy.springwebapp.order.repositories;

import com.greenfoxacademy.springwebapp.order.models.Order;
import com.greenfoxacademy.springwebapp.order.models.OrderStatus;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
  List<Order> findAllByDeliveryDeadlineLessThanEqualAndStatusIn(LocalDate date, List<OrderStatus> statuses);

}
