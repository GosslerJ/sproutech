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
@Table(name = "packages")
public class Package {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String uuid;

  @Column(name = "package_code")
  private String packageCode;

  @Column(name = "package_type")
  private String packageType;

  @ManyToOne(fetch = FetchType.EAGER)
  private Product product;

  @ManyToMany
  @JoinTable(
          name = "package_level",
          joinColumns = @JoinColumn(name = "package_id"),
          inverseJoinColumns = @JoinColumn(name = "level_id")
  )
  private Set<Level> levels;

}
