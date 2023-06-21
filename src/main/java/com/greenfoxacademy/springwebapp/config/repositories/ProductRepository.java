package com.greenfoxacademy.springwebapp.config.repositories;

import com.greenfoxacademy.springwebapp.config.models.HProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<HProduct, Integer> {
  List<HProduct> findAll();

}
