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

  @ManyToMany(mappedBy = "packageLevels")
  private Set<HCover> covers;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "packageLevel", fetch = FetchType.LAZY, orphanRemoval = true)
  private Set<HInsuredObject> insuredObjects;

}