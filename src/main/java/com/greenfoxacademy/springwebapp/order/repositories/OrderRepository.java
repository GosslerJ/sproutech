package com.greenfoxacademy.springwebapp.order.repositories;

import com.greenfoxacademy.springwebapp.order.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
