INSERT INTO admins (username, password, email) VALUES
    ('defaultUser1', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser1@email.com'),
    ('defaultUser2', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser2@email.com'),
    ('defaultUser3', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser3@email.com'),
    ('defaultUser4', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser4@email.com'),
    ('defaultUser5', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser5@email.com'),
    ('defaultUser6', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser6@email.com');

INSERT INTO customers (name, contact_person, email, phone_number, zip_code, city, address) VALUES
    ('defaultCompany1', 'John Doe', 'defaultCompany1@email.com', '123456789', '1234', 'Budapest', 'Kossuth Lajos utca 1.'),
    ('defaultCompany2', 'Gipsz Jakab', 'defaultCompany2@email.com', '987654321', '4321', 'Szeged', 'Kossuth Lajos utca 2.'),
    ('defaultCompany3', 'Max Mustermann', 'defaultCompany3@email.com', '123456789', '1234', 'Budapest', 'Kossuth Lajos utca 3.'),
    ('defaultCompany4', 'Besenyő István', 'defaultCompany4@email.com', '987654321', '4321', 'Szeged', 'Kossuth Lajos utca 4.'),
    ('defaultCompany5', 'Nagy Árpád', 'defaultCompany5@email.com', '123456789', '1234', 'Budapest', 'Kossuth Lajos utca 5.'),
    ('defaultCompany6', 'Bélabá', 'defaultCompany6@email.com', '987654321', '4321', 'Szeged', 'Kossuth Lajos utca 6.');

INSERT INTO materials (quality, size, hit_number, quantity, unit_price, unit_length, unit_weight) VALUES
    ('25CrMo4', 10.68, 204073, 164, 938, 3.08, 0.703),
    ('21CrMoV57', 14.6, 6465130005, 58, 1462, 4.08, 1.3135),
    ('42CrMo4', 21.85, 578000, 32, 952, 3.07, 1.314),
    ('C45', 22, 46433, 5, 490, 3.06, 1.988),
    ('1.7711', 35, 267237, 8, 1525, 6.084, 9.860),
    ('1.4923', 18.18, 402141, 7, 4995, 3.928, 2.037);

INSERT INTO orders (status, order_date, delivery_deadline, customer_id) VALUES
    ('NEW', '2023-04-01', '2023-05-02', 1),
    ('IN_PROGRESS', '2023-04-10', '2023-05-01', 2),
    ('DONE', '2023-04-02', '2023-04-20', 3),
    ('DELIVERED', '2023-04-03', '2023-04-18', 4),
    ('IN_PROGRESS', '2023-04-05', '2023-04-15', 5),
    ('DONE', '2023-04-01', '2023-04-17', 6);

INSERT INTO products (name, quality, size, length, quantity) VALUES
    ('szegcsavar1', '25 CrMo 4', 10.68, 3.08, 23),
    ('szegcsavar2', '21 CrMoV 57', 14.6, 4.08, 1),
    ('szegcsavar anya3', '42 CrMo 4', 21.85, 3.07, 12),
    ('szegcsavar anya4', 'C 45', 22, 3.06, 2),
    ('menetes rúd5', '1.7711', 35, 6.084, 6),
    ('menetes rúd6', '1.4923', 18.18, 3.928, 3);

INSERT INTO warehouses (name, address, zip_code, city) VALUES
    ('external', 'defaultAddress1', '1234', 'Budapest'),
    ('internal', 'defaultAddress2', '4321', 'Szeged');

INSERT INTO order_product (order_id, product_id) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 4),
    (2, 5),
    (2, 6),
    (3, 1),
    (3, 2),
    (3, 3),
    (4, 4),
    (4, 5),
    (4, 6),
    (5, 1),
    (5, 2),
    (5, 3),
    (6, 4),
    (6, 5),
    (6, 6);

INSERT INTO material_product (material_id, product_id) VALUES
    (1, 1),
    (2, 1),
    (3, 1),
    (4, 1),
    (5, 1),
    (6, 1),
    (1, 2),
    (2, 2),
    (3, 2),
    (4, 2),
    (5, 2),
    (6, 2),
    (1, 3),
    (2, 3),
    (3, 3),
    (4, 3),
    (5, 3),
    (6, 3),
    (1, 4),
    (2, 4),
    (3, 4),
    (4, 4),
    (5, 4),
    (6, 4),
    (1, 5),
    (2, 5),
    (3, 5),
    (4, 5),
    (5, 5),
    (6, 5),
    (1, 6),
    (2, 6),
    (3, 6),
    (4, 6),
    (5, 6),
    (6, 6);
