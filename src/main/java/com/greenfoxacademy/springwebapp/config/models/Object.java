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
public class Object {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "object_code")
  private String objectCode;

  @Column(name = "max_number_of_objects")
  private Integer maxNumberOfObjects;

  @Column(name = "is_mandatory")
  private Boolean isMandatory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "level_id")
  private Level level;
}