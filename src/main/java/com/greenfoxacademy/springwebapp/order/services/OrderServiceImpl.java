package com.greenfoxacademy.springwebapp.order.services;

import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.customer.models.Customer;
import com.greenfoxacademy.springwebapp.customer.repositories.CustomerRepository;
import com.greenfoxacademy.springwebapp.order.models.Order;
import com.greenfoxacademy.springwebapp.order.models.OrderRequestDTO;
import com.greenfoxacademy.springwebapp.order.models.OrderResponseDTO;
import com.greenfoxacademy.springwebapp.order.repositories.OrderRepository;
import com.greenfoxacademy.springwebapp.product.models.Product;
import com.greenfoxacademy.springwebapp.product.models.ProductStatus;
import com.greenfoxacademy.springwebapp.product.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.greenfoxacademy.springwebapp.order.models.OrderStatus.IN_PROGRESS;
import static com.greenfoxacademy.springwebapp.order.models.OrderStatus.NEW;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

  private CustomerRepository customerRepository;
  private OrderRepository orderRepository;
  private ProductRepository productRepository;

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
      product.setStatus(ProductStatus.NEW);
      productRepository.save(product);
    }
    orderRepository.save(order);
    return new OrderResponseDTO(order.getId(), order.getCustomer().getId(), order.getOrderedProducts());
  }

  @Override
  public void deleteOrderById(Integer id) {
    Optional<Order> optionalOrder = orderRepository.findById(id);
    if (optionalOrder.isPresent()) {
      Order order = optionalOrder.get();
      orderRepository.delete(order);
    } else {
      throw new IdNotFoundException();
    }
  }

  @Override
  public List<Order> filterOrdersByDeliveryDeadline(Integer days) {
    if (days == null) {
      return (List<Order>) orderRepository.findAll();
    } else {
      LocalDate date = LocalDate.now().plusDays(days);
      return orderRepository.findAllByDeliveryDeadlineLessThanEqualAndStatusIn(date, Arrays.asList(NEW, IN_PROGRESS));
    }
  }

}
