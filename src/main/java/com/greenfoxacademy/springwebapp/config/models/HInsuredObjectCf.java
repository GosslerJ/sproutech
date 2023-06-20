package com.greenfoxacademy.springwebapp.config.models;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "objects")
public class HInsuredObjectCf {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "insured_object_code")
  private String objectCode;

  @Column(name = "number_of_insured_objects")
  private int numberOfInsuredObjects;
  @Column(name = "max_number_of_insured_objects")
  private Integer maxNumberOfObjects;

  @Column(name = "is_mandatory")
  private Boolean isMandatory;

  @Column(name = "sum_insured_min")
  private double sumInsuredMin;

  @Column(name = "sum_insured_max")
  private double sumInsuredMax;

  @Column(name = "sum_insured_scale")
  private String sumInsuredScale;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "level_id")
  private HPackageLevelCf HPackageLevelCf;

}