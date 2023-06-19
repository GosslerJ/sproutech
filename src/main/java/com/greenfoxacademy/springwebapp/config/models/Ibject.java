package com.greenfoxacademy.springwebapp.config.models;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ibjects")
public class Ibject {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "ibject_code")
  private String ibjectCode;

  @Column(name = "max_number_of_ibjects")
  private Integer maxNumberOfIbjects;

  @Column(name = "is_mandatory")
  private Boolean isMandatory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "level_id")
  private Level level;
}