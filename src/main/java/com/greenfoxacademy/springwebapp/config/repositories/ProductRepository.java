package com.greenfoxacademy.springwebapp.config.repositories;

import com.greenfoxacademy.springwebapp.config.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {
  List<Product> findAll();

}
