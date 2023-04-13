package com.greenfoxacademy.springwebapp.material.repositories;

import com.greenfoxacademy.springwebapp.material.models.Material;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MaterialRepository extends CrudRepository<Material, Integer> {

  List<Material> findAll();

  List<Material> findAllByQualityAndSize(String quality, Double size);

  List<Material> findAllByQualityAndSizeAndWarehouseId(String quality, Double size, Integer id);

  List<Material> findAllByQuality(String quality);

  List<Material> findAllBySize(Double size);
}
