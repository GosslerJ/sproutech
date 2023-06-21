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
@Table(name = "cf_covers")
public class HCover {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "cover_code")
  private String coverCode;

  @Column(name = "is_mandatory")
  private Boolean isMandatory;

  @Column(name = "is_premium_free")
  private Boolean isPremiumFree;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "cover", fetch = FetchType.LAZY, orphanRemoval = true)
  private Set<HPeril> perils;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "cover", fetch = FetchType.LAZY, orphanRemoval = true)
  private Set<HLimit> limits;

  @JsonIgnore
  @ManyToMany
  @JoinTable(
          name = "cf_cover_level",
          joinColumns = @JoinColumn(name = "cf_cover_id"),
          inverseJoinColumns = @JoinColumn(name = "cf_level_id")
  )
  private Set<HPackageLevel> packageLevels;

}
