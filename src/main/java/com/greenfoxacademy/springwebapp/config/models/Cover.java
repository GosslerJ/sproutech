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
@Table(name = "covers")
public class Cover {

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
  private Set<Peril> perils;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "cover", fetch = FetchType.LAZY, orphanRemoval = true)
  private Set<Limit> limits;

  @JsonIgnore
  @ManyToMany
  @JoinTable(
          name = "cover_level",
          joinColumns = @JoinColumn(name = "cover_id"),
          inverseJoinColumns = @JoinColumn(name = "level_id")
  )
  private Set<Level> levels;

}
