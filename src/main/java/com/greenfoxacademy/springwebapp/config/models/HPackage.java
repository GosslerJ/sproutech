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
@Table(name = "cf_packages")
public class HPackage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String uuid;

  @Column(name = "package_code")
  private String packageCode;

  @Column(name = "package_type")
  private String packageType;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER)
  private HProduct product;

  @ManyToMany
  @JoinTable(
          name = "cf_package_level",
          joinColumns = @JoinColumn(name = "cf_package_id"),
          inverseJoinColumns = @JoinColumn(name = "cf_level_id")
  )
  private Set<HPackageLevel> packageLevels;

}
