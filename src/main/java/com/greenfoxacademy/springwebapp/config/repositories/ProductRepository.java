package com.greenfoxacademy.springwebapp.config.repositories;

import com.greenfoxacademy.springwebapp.config.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {
  List<Product> findAll();
  Optional<Product> findById(Long id);

}
