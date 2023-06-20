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
@Table(name = "products")
public class HProductCf {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "product_code")
  private String code;
  @Column(name = "insurance_company_code")
  private String insuranceCompanyCode;

  @Column(name = "is_dynamic")
  private Boolean isDynamic;

  @Column(name = "standardized_code")
  private String standardizedCode;
  @Column(name = "product_version_from")
  private Integer productVersionFrom;

  @Column(name = "product_version_to")
  private Integer productVersionTo;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "HProductCf", orphanRemoval = true)
  private Set<HPackageCf> HPackageCfs;

  @Column(name = "thousand_inputs_enabled")
  private Boolean thousandInputsEnabled;

  @Column(name = "frame_offer_creation_enabled")
  private Boolean frameOfferCreationEnabled;

  @Column(name = "frame_quote_creation_enabled")
  private Boolean frameQuoteCreationEnabled;

  @Column(name = "frame_policy_creation_enabled")
  private Boolean framePolicyCreationEnabled;

  @Column(name = "offer_creation_enabled")
  private Boolean offerCreationEnabled;

  @Column(name = "quote_creation_enabled")
  private Boolean quoteCreationEnabled;

  @Column(name = "policy_creation_enabled")
  private Boolean policyCreationEnabled;

  @Column(name = "is_frame_product")
  private Boolean isFrameProduct;

  @Column(name = "package_type")
  private String packageType;

  @Column(name = "max_discount_pct")
  private int maxDiscountPct;

  @Column(name = "is_strict_prod_version")
  private Boolean isStrictProdVersion;

  @Column(name = "is_claim_clearing_enabled")
  private Boolean isClaimClearingEnabled;

  @Column(name = "is_intervention_enabled")
  private Boolean isInterventionEnabled;

  @Column(name = "intervention_tolerance")
  private Boolean interventionTolerance;

  @Column(name = "use_wip")
  private Boolean useWip;

  @Column(name = "delete_empty_frame")
  private Boolean deleteEmptyFrame;

  @Column(name = "invoicing_after_prem_wr")
  private Boolean invoicingAfterPremWr;

  @Column(name = "calc_saldo")
  private Boolean calcSaldo;

}