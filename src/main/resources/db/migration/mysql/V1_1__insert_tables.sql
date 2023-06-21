INSERT INTO cf_products (
  product_code, insurance_company_code, is_dynamic, product_version_from, product_version_to,
  thousand_inputs_enabled, frame_offer_creation_enabled, frame_quote_creation_enabled, frame_policy_creation_enabled,
  offer_creation_enabled, quote_creation_enabled, policy_creation_enabled, is_frame_product, package_type,
  is_strict_prod_version, is_claim_clearing_enabled, is_intervention_enabled, use_wip, delete_empty_frame,
  invoicing_after_prem_wr, calc_saldo
) VALUES ('VALLALKOZAS', 'Magyar Posta Biztosító Zrt.', 1, 1, 9999, 1, 0, 0, 0, 1, 1, 1, 0, NULL, 1, 1, 0, 0, 0, 1, 1),
('CASCO', 'Magyar Posta Biztosító Zrt.', 1, 1, 9999, 0, 0, 0, 0, 0, 1, 0, 0, NULL, 1, 1, 0, 0, 0, 1, 1),
('16090', 'Magyar Posta Biztosító Zrt.', 1, 1, 9999, 0, 0, 0, 0, 0, 1, 0, 0, 'MULTI', 1, 1, 0, 0, 0, 1, 1);

INSERT INTO cf_packages (uuid, package_code, package_type, product_id)
VALUES ('U1', 'VAROSLATOGATAS', 'UTAS', 3),
('U1', 'TENGERPART', 'UTAS', 3),
('U1', 'TURA', 'UTAS', 3),
('U1', 'NYARI_TELI_SPORT', 'UTAS', 3),
('U1', 'EXTREM_SPORT', 'UTAS', 3),
('U1', 'AUTO_ASSZISZTENCIA', 'UTAS', 3),
('U1', 'JARATKESES', 'UTAS', 3);

INSERT INTO cf_levels (level_code)
VALUES ('START'),
('OPTIMUM'),
('PREMIUM'),
('BASIC');

INSERT INTO cf_covers (cover_code, is_mandatory, is_premium_free)
VALUES ('EGESSZEG_SEGITSEGNYUJTAS', 1, 0),
('EGYEB_SEGITSEGNYUJTAS', 1, 0),
('BALESETI_HALAL', 1, 0),
('BALESETI_ROKKANTSAG', 1, 0),
('BALESETI_TAPPENZ', 1, 0),
('POGGYASZ', 1, 0),
('FELELOSSEG', 1, 0),
('JOGVEDELEM', 1, 0),
('EGYEB_JOGI_KOLTSEGEK', 1, 0),
('AUTO_ASSZISZTENCIA', 1, 0),
('JARATKESES', 1, 0);

INSERT INTO cf_limits (uuid, limit_type, limit_min, limit_max, limit_range, is_visible, cf_cover_id, cf_peril_id)
VALUES ('U1', 'LIMIT_PER_CLAIM_AMOUNT', 1600000, 1600000, 0, 1, 7, NULL),
('U1', 'LIMIT_PER_TERM_AMOUNT', 1600000, 1600000, 0, 1, 7, NULL),
('U1', 'LIMIT_PER_CLAIM_AMOUNT', 400000, 400000, 0, 1, 8, NULL),
('U1', 'LIMIT_PER_TERM_AMOUNT', 400000, 400000, 0, 1, 8, NULL),
('U1', 'LIMIT_PER_CLAIM_AMOUNT', 800000, 800000, 0, 1, 9, NULL),
('U1', 'LIMIT_PER_TERM_AMOUNT', 800000, 800000, 0, 1, 9, NULL),
('U1', 'LIMIT_PER_CLAIM_AMOUNT', 2000000, 2000000, 0, 1, 7, NULL),
('U1', 'LIMIT_PER_TERM_AMOUNT', 2000000, 2000000, 0, 1, 7, NULL),
('U1', 'LIMIT_PER_CLAIM_AMOUNT', 500000, 500000, 0, 1, 8, NULL),
('U1', 'LIMIT_PER_TERM_AMOUNT', 500000, 500000, 0, 1, 8, NULL),
('U1', 'LIMIT_PER_CLAIM_AMOUNT', 1000000, 1000000, 0, 1, 9, NULL),
('U1', 'LIMIT_PER_TERM_AMOUNT', 1000000, 1000000, 0, 1, 9, NULL),
('U1', 'LIMIT_PER_CLAIM_AMOUNT', 2400000, 2400000, 0, 1, 7, NULL),
('U1', 'LIMIT_PER_TERM_AMOUNT', 2400000, 2400000, 0, 1, 7, NULL),
('U1', 'LIMIT_PER_CLAIM_AMOUNT', 600000, 600000, 0, 1, 8, NULL),
('U1', 'LIMIT_PER_TERM_AMOUNT', 600000, 600000, 0, 1, 8, NULL),
('U1', 'LIMIT_PER_CLAIM_AMOUNT', 1200000, 1200000, 0, 1, 9, NULL),
('U1', 'LIMIT_PER_TERM_AMOUNT', 1200000, 1200000, 0, 1, 9, NULL);

INSERT INTO cf_perils (peril_code, uuid, cf_cover_id)
VALUES ('DIRECT_MEDICAL_COSTS', 'U1', 1),
('URGENT_DENTAL_CARE', 'U1', 1),
('EYEGLASS_REPLACEMENT_PERSONAL_INJURY', 'U1', 1),
('MEDICAL_TRANSPORTATION_SERVICES', 'U1', 1),
('MOUNTAIN_WATER_HELICOPTER_RESCUE', 'U1', 1),
('CORPSE_TRANSPORTATION', 'U1', 1),
('TERRORISM', 'U1', 1),
('EXTENSION_OF_STAY', 'U1', 2),
('CHILD_SICKNESS_ACCOMMODATION_TRAVEL_COSTS', 'U1', 2),
('FAMILY_ACCOMMODATION_COSTS', 'U1', 2),
('DRIVER_COSTS', 'U1', 2),
('INSURED_PERSON_REPATRIATION', 'U1', 2),
('ACCIDENT_SICKNESS_OTHER_COSTS', 'U1', 2),
('EARLY_DEPARTURE', 'U1', 2),
('CHILD_REPATRIATION', 'U1', 2),
('ACCIDENTAL_DEATH', 'U1', 3),
('ACCIDENTAL_DISABILITY', 'U1', 4),
('ACCIDENTAL_INCAPACITY_FOR_WORK', 'U1', 5),
('THEFT_OR_DAMAGE_OF_LUGGAGE', 'U1', 6),
('THEFT_OR_DAMAGED_OF_PHOTOGRAPHED_OBJECT', 'U1', 6),
('TRAVEL_DOCUMENTS_REPLACEMENT_COSTS', 'U1', 6),
('LAWYER_COSTS', 'U1', 7),
('EXPERT_COSTS', 'U1', 7),
('BAIL', 'U1', 8),
('LEVY', 'U1', 8),
('LEGAL_EXPENSES', 'U1', 8),
('GENERAL_LIABILITY', 'U1', 9);

INSERT INTO cf_objects (insured_object_code, max_number_of_insured_objects, is_mandatory, cf_level_id)
VALUES ('PERSON', 9999, 1, 1),
('VEHICLE', 9999, 0, 2);

INSERT INTO cf_package_level (cf_package_id, cf_level_id)
VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(2, 3),
(3, 1),
(3, 2),
(3, 3),
(4, 1),
(4, 2),
(4, 3),
(5, 1),
(5, 2),
(5, 3),
(6, 4),
(7, 4);

INSERT INTO cf_cover_level (cf_level_id, cf_cover_id)
VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),

(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 9),

(3, 1),
(3, 2),
(3, 3),
(3, 4),
(3, 5),
(3, 6),
(3, 7),
(3, 8),
(3, 9),

(4, 10),
(4, 11);