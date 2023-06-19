package com.greenfoxacademy.springwebapp.admin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDTO {

  private int id;
  private String username;

}