package com.greenfoxacademy.springwebapp.config.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "levels")
public class Level {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "level_code")
  private String packageLevelCode;

  @ManyToMany(mappedBy = "levels")
  private Set<Cover> covers; //*

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "level", fetch = FetchType.LAZY, orphanRemoval = true)
  private Set<Ibject> ibjects; //*

}