DELETE FROM products;
DELETE FROM materials;
DELETE FROM warehouses;
DELETE FROM orders;
DELETE FROM customers;
DELETE FROM admins;

INSERT INTO admins (id, username, password, email) VALUES
    (1001, 'defaultUser1', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser1@email.com'),
    (1002, 'defaultUser2', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser2@email.com'),
    (1003, 'defaultUser3', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser3@email.com'),
    (1004, 'defaultUser4', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser4@email.com'),
    (1005, 'defaultUser5', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser5@email.com'),
    (1006, 'defaultUser6', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser6@email.com');

INSERT INTO customers (id, name, contact_person, email, phone_number, zip_code, city, address) VALUES
    (1001, 'defaultCompany1', 'John Doe', 'defaultCompany1@email.com', '123456789', '1234', 'Budapest', 'Kossuth Lajos utca 1.'),
    (1002, 'defaultCompany2', 'Gipsz Jakab', 'defaultCompany2@email.com', '987654321', '4321', 'Szeged', 'Kossuth Lajos utca 2.'),
    (1003, 'defaultCompany3', 'Max Mustermann', 'defaultCompany3@email.com', '123456789', '1234', 'Budapest', 'Kossuth Lajos utca 3.'),
    (1004, 'defaultCompany4', 'Besenyő István', 'defaultCompany4@email.com', '987654321', '4321', 'Szeged', 'Kossuth Lajos utca 4.'),
    (1005, 'defaultCompany5', 'Nagy Árpád', 'defaultCompany5@email.com', '123456789', '1234', 'Budapest', 'Kossuth Lajos utca 5.'),
    (1006, 'defaultCompany6', 'Bélabá', 'defaultCompany6@email.com', '987654321', '4321', 'Szeged', 'Kossuth Lajos utca 6.');

INSERT INTO orders (id, status, order_date, delivery_deadline, customer_id) VALUES
    (1001, 'NEW', '2023-04-01', '2023-05-02', 1001),
    (1002, 'IN_PROGRESS', '2023-04-10', '2023-05-01', 1002),
    (1003, 'READY', '2023-04-02', '2023-04-20', 1003),
    (1004, 'NEW', '2023-04-03', '2023-04-18', 1004),
    (1005, 'IN_PROGRESS', '2023-04-05', '2023-04-15', 1005),
    (1006, 'READY', '2023-04-01', '2023-04-17', 1006);

INSERT INTO warehouses (id, name, address, zip_code, city) VALUES
    (1, 'external', 'defaultAddress1', '1234', 'Budapest'),
    (2, 'internal', 'defaultAddress2', '4321', 'Szeged');

INSERT INTO materials (id, quality, size, hit_number,unit_price, unit_length, unit_weight, original_weight, original_length, remaining_weight, remaining_length, warehouse_id) VALUES
    (1001, '25CrMo4', 10.68, 204073, 938, 3.08, 0.703, 5000, 6000, 5000, 6000, 1),
    (1002, '21CrMoV57', 14.6, 6465130005, 1462, 4.08, 1.3135, 8000, 1200, 8000, 1200, 1),
    (1003, '42CrMo4', 18.18, 578000, 952, 3.07, 1.314, 1300, 1000, 1300, 1000, 1),
    (1004, '25CrMo4', 10.68, 46433, 490, 3.06, 1.988, 4800, 2400, 4800, 2400, 1),
    (1005, '21CrMoV57', 21.84, 267237, 1525, 6.084, 9.860, 11000, 1100, 11000, 1100, 2),
    (1006, '42CrMo4', 21.96, 402141, 4995, 3.928, 2.037, 1600, 800, 1600, 800, 2);

INSERT INTO products (id, status, name, quality, size, length, quantity) VALUES
    (1001, 'NEW', 'szegcsavar1', '25CrMo4', 12, 80, 23),
    (1002, 'NEW', 'szegcsavar2', '21CrMoV57', 16, 60, 10),
    (1003, 'NEW', 'szegcsavar anya3', '42CrMo4', 20, 75, 12),
    (1004, 'NEW', 'szegcsavar anya4', '25CrMo4', 20, 120, 20),
    (1005, 'NEW', 'menetes rúd5', '1.21CrMoV57', 24, 150, 60),
    (1006, 'NEW', 'menetes rúd6', '42CrMo4', 24, 110, 35);
