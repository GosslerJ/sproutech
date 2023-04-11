package com.greenfoxacademy.springwebapp.material.services;

import com.greenfoxacademy.springwebapp.material.models.MaterialRequestDTO;
import com.greenfoxacademy.springwebapp.material.models.MaterialResponseDTO;

public interface MaterialService {
  MaterialResponseDTO saveMaterial(MaterialRequestDTO materialRequestDTO);
}
