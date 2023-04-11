package com.greenfoxacademy.springwebapp.customer.models;

import com.greenfoxacademy.springwebapp.order.models.Order;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String contactPerson;
  private String email;
  private String phoneNumber;
  private String taxNumber;
  private String zipCode;
  private String city;
  private String address;
  private String country;
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  private List<Order> orders;

}
