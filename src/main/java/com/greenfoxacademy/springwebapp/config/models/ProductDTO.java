package com.greenfoxacademy.springwebapp.config.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public class ProductDTO {

    private Integer id;
    private String code;
    private Integer productVersionFrom;
    private Integer productVersionTo;
    private String packageCf;

  }
