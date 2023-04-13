package com.greenfoxacademy.springwebapp.warehouse.repositories;

import com.greenfoxacademy.springwebapp.warehouse.models.Warehouse;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WarehouseRepository extends CrudRepository<Warehouse, Integer> {
  @Override
  Optional<Warehouse> findById(Integer integer);
}
