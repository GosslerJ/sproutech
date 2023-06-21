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
@Table(name = "cf_limits")
public class HLimit {

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
  @JoinColumn(name = "cf_cover_id")
  private HCover cover;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cf_peril_id")
  private HPeril peril;

}