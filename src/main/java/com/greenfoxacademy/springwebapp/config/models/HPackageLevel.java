package com.greenfoxacademy.springwebapp.config.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cf_levels")
public class HPackageLevel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "level_code")
  private String packageLevel;

  @ManyToMany(mappedBy = "HPackageLevels")
  private Set<HCover> HCovers;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "HPackageLevel", fetch = FetchType.LAZY, orphanRemoval = true)
  private Set<HInsuredObject> HInsuredObjects;

}