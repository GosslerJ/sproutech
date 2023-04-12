package com.greenfoxacademy.springwebapp.material.repositories;

import com.greenfoxacademy.springwebapp.material.models.Material;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends CrudRepository<Material, Integer> {

  List<Material> findAll();

  List<Material> findAllByQualityAndSize(String s, Float aFloat);

  List<Material> findAllByQuality(String s);

  List<Material> findAllBySize(Float aFloat);
}
