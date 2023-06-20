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
@Table(name = "perils")
public class Peril {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "peril_code")
  private String perilCode;

  @Column(name = "uuid")
  private String uuid;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cover_id")
  private Cover cover;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "peril", fetch = FetchType.LAZY, orphanRemoval = true)
  private Set<Limit> limits;

}