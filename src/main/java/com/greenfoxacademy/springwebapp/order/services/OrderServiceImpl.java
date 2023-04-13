package com.greenfoxacademy.springwebapp.order.services;

import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.customer.models.Customer;
import com.greenfoxacademy.springwebapp.customer.repositories.CustomerRepository;
import com.greenfoxacademy.springwebapp.order.models.Order;
import com.greenfoxacademy.springwebapp.order.models.OrderRequestDTO;
import com.greenfoxacademy.springwebapp.order.models.OrderResponseDTO;
import com.greenfoxacademy.springwebapp.order.models.OrderStatus;
import com.greenfoxacademy.springwebapp.order.repositories.OrderRepository;
import com.greenfoxacademy.springwebapp.product.models.Product;
import com.greenfoxacademy.springwebapp.product.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.greenfoxacademy.springwebapp.order.models.OrderStatus.*;
import static com.greenfoxacademy.springwebapp.order.models.OrderStatus.NEW;

@Service
public class OrderServiceImpl implements OrderService {

  private CustomerRepository customerRepository;
  private OrderRepository orderRepository;
  private ProductRepository productRepository;

  public OrderServiceImpl(CustomerRepository customerRepository,
                          OrderRepository orderRepository, ProductRepository productRepository) {
    this.customerRepository = customerRepository;
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
  }

  @Override
  public OrderResponseDTO saveOrder(OrderRequestDTO dto) {
    Customer customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(IdNotFoundException::new);
    Order order = Order.builder()
            .customer(customer)
            .orderDate(dto.getOrderDate())
            .deliveryDeadline(dto.getDeliveryDeadline())
            .build();
    order.setStatus(NEW);
    List<Product> products = new ArrayList<>();
    products.addAll(dto.getOrderedProducts());
    order.setOrderedProducts(products);
    for (Product product : products) {
      productRepository.save(product);
    }
    orderRepository.save(order);
    return new OrderResponseDTO(order.getId(), order.getCustomer().getId(), order.getOrderedProducts());
  }

  @Override
  public void deleteOrderById(Integer id) {
    orderRepository.deleteById(id);
  }

  @Override
  public List<Order> filterOrdersByDeliveryDeadline(Integer days) {
    if (days == null) {
      return (List<Order>) orderRepository.findAll();
    } else {
      LocalDate date = LocalDate.now().plusDays(days);
      return orderRepository.findAllByDeliveryDeadlineLessThanEqualAndStatusIn(date, List.of(NEW, IN_PROGRESS));
    }
  }
}
