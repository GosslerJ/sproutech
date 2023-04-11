package com.greenfoxacademy.springwebapp.material.repositories;

import com.greenfoxacademy.springwebapp.material.models.Material;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends CrudRepository<Material, Integer> {
  Optional<Material> findByQuality(String quality);

  List<Material> findAll();

}
