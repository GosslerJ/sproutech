package com.greenfoxacademy.springwebapp.product.repositories;

import com.greenfoxacademy.springwebapp.product.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
