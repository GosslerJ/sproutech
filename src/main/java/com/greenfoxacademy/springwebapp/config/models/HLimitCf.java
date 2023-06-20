package com.greenfoxacademy.springwebapp.config.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "limits")
public class HLimitCf {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "uuid")
  private String uuid;

  @Column(name = "limit_type")
  private String limitType;

  @Column(name = "limit_min")
  private Integer limitMin;

  @Column(name = "limit_max")
  private Integer limitMax;

  @Column(name = "limit_range")
  private Integer limitRange;

  @Column(name = "limit_amount")
  private BigDecimal limitAmount;

  @Column(name = "limit_days")
  private Long limitDays;

  @Column(name = "is_visible")
  private Boolean isVisible;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cover_id")
  private HCoverCf HCoverCf;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "peril_id")
  private HCoverCf peril;

}