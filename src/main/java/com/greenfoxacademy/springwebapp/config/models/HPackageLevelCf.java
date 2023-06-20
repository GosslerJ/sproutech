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
@Table(name = "levels")
public class HPackageLevelCf {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "level_code")
  private String packageLevel;

  @ManyToMany(mappedBy = "HPackageLevelCfs")
  private Set<HCoverCf> HCoverCfs;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "HPackageLevelCf", fetch = FetchType.LAZY, orphanRemoval = true)
  private Set<HInsuredObjectCf> HInsuredObjectCfs;

}