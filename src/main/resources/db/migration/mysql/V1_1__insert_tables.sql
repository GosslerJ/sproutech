INSERT INTO products (product_code, product_version_from, product_version_to)
VALUES ('VALLALKOZAS', 1, 9999),
('CASCO', 1, 9999),
('16090', 1, 9999);

INSERT INTO packages (uuid, package_code, package_type, product_id)
VALUES ('U1', 'VAROSLATOGATAS', 'UTAS', 3),
('U2', 'TENGERPART', 'UTAS', 3),
('U3', 'TURA', 'UTAS', 3),
('U4', 'NYARI_TELI_SPORT', 'UTAS', 3),
('U5', 'EXTREM_SPORT', 'UTAS', 3),
('U6', 'AUTO_ASSZISZTENCIA', 'UTAS', 3),
('U7', 'JARATKESES', 'UTAS', 3);

INSERT INTO levels (level_code)
VALUES ('START'),
('OPTIMUM'),
('BASIC'),
('PREMIUM');

INSERT INTO covers (cover_code, is_mandatory, is_premium_free)
VALUES ('EGESSZEG_SEGITSEGNYUJTAS', TRUE, FALSE),
('EGYEB_SEGITSEGNYUJTAS', TRUE, FALSE),
('BALESETI_HALAL', TRUE, FALSE),
('BALESETI_ROKKANTSAG', TRUE, FALSE),
('BALESETI_TAPPENZ', TRUE, FALSE),
('POGGYASZ', TRUE, FALSE),
('FELELOSSEG', TRUE, FALSE),
('JOGVEDELEM', TRUE, FALSE),
('AUTO_ASSZISZTENCIA', TRUE, FALSE),
('JARATKESES', TRUE, FALSE);

INSERT INTO perils (peril_code, uuid, cover_id)
VALUES ('DIRECT_MEDICAL_COSTS', 'U1', 1),
('URGENT_DENTAL_CARE', 'U1', 1),
('EYEGLASS_REPLACEMENT_PERSONAL_INJURY', 'U1', 1),
('MEDICAL_TRANSPORTATION_SERVICES', 'U1', 1),
('MOUNTAIN_WATER_HELICOPTER_RESCUE', 'U1', 1),
('CORPSE_TRANSPORTATION', 'U1', 1),
('TERRORISM', 'U1', 1),
('EXTENSION_OF_STAY', 'U2', 2),
('CHILD_SICKNESS_ACCOMMODATION_TRAVEL_COSTS', 'U2', 2),
('FAMILY_ACCOMMODATION_COSTS', 'U2', 2),
('DRIVER_COSTS', 'U2', 2),
('INSURED_PERSON_REPATRIATION', 'U2', 2),
('ACCIDENT_SICKNESS_OTHER_COSTS', 'U2', 2),
('EARLY_DEPARTURE', 'U2', 2),
('CHILD_REPATRIATION', 'U2', 2),
('ACCIDENTAL_DEATH', 'U3', 3),
('ACCIDENTAL_DISABILITY', 'U3', 4),
('ACCIDENTAL_INCAPACITY_FOR_WORK', 'U3', 5),
('THEFT_OR_DAMAGE_OF_LUGGAGE', 'U3', 6),
('THEFT_OR_DAMAGED_OF_PHOTOGRAPHED_OBJECT', 'U3', 6),
('TRAVEL_DOCUMENTS_REPLACEMENT_COSTS', 'U3', 6),
('LAWYER_COSTS', 'U3', 7),
('EXPERT_COSTS', 'U3', 7),
('BAIL', 'U3', 8),
('LEVY', 'U3', 8),
('LEGAL_EXPENSES', 'U3', 8),
('GENERAL_LIABILITY', 'U3', 9);

INSERT INTO limits (uuid, limit_type, limit_min, limit_max, limit_range, limit_amount, limit_days, is_visible, cover_id, peril_id)
VALUES ('U1', 'LIMIT_PER_CLAIM_AMOUNT', 1, 100, 0, 5000.00, 365, 1, 1, 1),
('U2', 'LIMIT_PER_TERM_AMOUNT', 10, 500, 0, 10000.00, 180, 1, 2, 2),
('U3', 'LIMIT_PER_CLAIM_AMOUNT', 100, 1000, 0, 20000.00, 90, 1, 3, 3);

INSERT INTO objects (object_code, max_number_of_objects, is_mandatory, level_id)
VALUES ('PERSON', 5, TRUE, 1),
('VEHICLE', 10, FALSE, 2),
('O3', 3, TRUE, 3);

INSERT INTO package_level (package_id, level_id)
VALUES (1, 1),
(1, 2),
(2, 2),
(2, 3),
(3, 1),
(3, 3);

INSERT INTO cover_level (cover_id, level_id)
VALUES (1, 1),
(1, 2),
(2, 2),
(2, 3),
(3, 1),
(3, 3);