package com.greenfoxacademy.springwebapp.customer.repositories;

import com.greenfoxacademy.springwebapp.customer.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

  Optional<Customer> findById(Integer id);

  Optional<Customer> findByName(String name);

  List<Customer> findAll();

}
