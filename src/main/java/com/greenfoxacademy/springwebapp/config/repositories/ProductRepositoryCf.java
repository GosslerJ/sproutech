package com.greenfoxacademy.springwebapp.config.repositories;

import com.greenfoxacademy.springwebapp.config.models.HProductCf;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepositoryCf extends CrudRepository<HProductCf, Integer> {
  List<HProductCf> findAll();

}
