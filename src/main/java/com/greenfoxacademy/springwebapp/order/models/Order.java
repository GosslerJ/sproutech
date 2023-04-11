package com.greenfoxacademy.springwebapp.order.models;

import com.greenfoxacademy.springwebapp.customer.models.Customer;
import com.greenfoxacademy.springwebapp.product.models.Product;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(columnDefinition = "enum('new', 'in_progress', 'done', 'delivered', 'cancelled')")
  private OrderStatus status;
  private LocalDate orderDate;
  private LocalDate deliveryDeadline;
  private LocalDate deliveryDate;
  private Long deliveryNumber;
  @ManyToOne(fetch = FetchType.EAGER)
  private Customer customer;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "order_product",
          joinColumns = @JoinColumn(name = "order_id"),
          inverseJoinColumns = @JoinColumn(name = "product_id")
  )
  private List<Product> orderedProducts;
}
