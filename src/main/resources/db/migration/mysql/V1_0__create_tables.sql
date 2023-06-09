CREATE TABLE IF NOT EXISTS admins (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

ALTER TABLE admins ADD CONSTRAINT id UNIQUE (id);

INSERT INTO admins (username, password, email) VALUES
    ('defaultUser1', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser1@email.com'),
    ('defaultUser2', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser2@email.com'),
    ('defaultUser3', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser3@email.com'),
    ('defaultUser4', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser4@email.com'),
    ('defaultUser5', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser5@email.com'),
    ('defaultUser6', '$2a$12$tEjxtMugROz2HudoFd0/v.D55KeANXifp25p3nL5uVDTZcstJu.l2', 'defaultUser6@email.com');


CREATE TABLE IF NOT EXISTS customers (
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  contact_person VARCHAR(255),
  email VARCHAR(255),
  phone_number VARCHAR(20),
  tax_number VARCHAR(20),
  zip_code VARCHAR(10),
  city VARCHAR(255),
  address VARCHAR(255),
  country VARCHAR(255)
);

INSERT INTO customers (name, contact_person, email, phone_number, zip_code, city, address) VALUES
    ('defaultCompany1', 'John Doe', 'defaultCompany1@email.com', '123456789', '1234', 'Budapest', 'Kossuth Lajos utca 1.'),
    ('defaultCompany2', 'Gipsz Jakab', 'defaultCompany2@email.com', '987654321', '4321', 'Szeged', 'Kossuth Lajos utca 2.'),
    ('defaultCompany3', 'Max Mustermann', 'defaultCompany3@email.com', '123456789', '1234', 'Budapest', 'Kossuth Lajos utca 3.'),
    ('defaultCompany4', 'Besenyő István', 'defaultCompany4@email.com', '987654321', '4321', 'Szeged', 'Kossuth Lajos utca 4.'),
    ('defaultCompany5', 'Nagy Árpád', 'defaultCompany5@email.com', '123456789', '1234', 'Budapest', 'Kossuth Lajos utca 5.'),
    ('defaultCompany6', 'Bélabá', 'defaultCompany6@email.com', '987654321', '4321', 'Szeged', 'Kossuth Lajos utca 6.');


ALTER TABLE customers ADD CONSTRAINT id UNIQUE (id);

CREATE TABLE IF NOT EXISTS orders (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    status ENUM('new', 'in_progress', 'ready') NOT NULL,
    order_date DATE,
    delivery_deadline DATE,
    delivery_date DATE,
    delivery_number BIGINT,
    customer_id INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

INSERT INTO orders (status, order_date, delivery_deadline, customer_id) VALUES
    ('NEW', '2023-04-01', '2023-05-02', 1),
    ('IN_PROGRESS', '2023-04-10', '2023-05-01', 2),
    ('READY', '2023-04-02', '2023-04-20', 3),
    ('NEW', '2023-04-03', '2023-04-18', 4),
    ('IN_PROGRESS', '2023-04-05', '2023-04-15', 5),
    ('READY', '2023-04-01', '2023-04-17', 6);


CREATE TABLE IF NOT EXISTS warehouses (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name ENUM('external', 'internal', 'bag') NOT NULL,
  zip_code VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  address VARCHAR(255) NOT NULL
);

INSERT INTO warehouses (name, address, zip_code, city) VALUES
    ('external', 'defaultAddress1', '1234', 'Budapest'),
    ('internal', 'defaultAddress2', '4321', 'Szeged');


CREATE TABLE IF NOT EXISTS materials (
  id INT PRIMARY KEY AUTO_INCREMENT,
  quality VARCHAR(255) NOT NULL,
  size DOUBLE NOT NULL,
  hit_number BIGINT NOT NULL,
  unit_price BIGINT,
  unit_weight DOUBLE NOT NULL,
  unit_length DOUBLE NOT NULL,
  original_weight DOUBLE,
  remaining_weight DOUBLE,
  original_length DOUBLE,
  remaining_length DOUBLE,
  updated_at DATE DEFAULT (CURRENT_DATE),
  warehouse_id INT DEFAULT 1
);

INSERT INTO materials (quality, size, hit_number,unit_price, unit_length, unit_weight, original_weight, original_length, remaining_weight, remaining_length) VALUES
    ('25CrMo4', 10.68, 204073, 938, 3.08, 0.703, 5000, 6000, 5000, 6000),
    ('21CrMoV57', 14.6, 6465130005, 1462, 4.08, 1.3135, 8000, 1200, 8000, 1200),
    ('42CrMo4', 18.18, 578000, 952, 3.07, 1.314, 1300, 1000, 1300, 1000),
    ('25CrMo4', 18.3, 46433, 490, 3.06, 1.988, 4800, 2400, 4800, 2400),
    ('21CrMoV57', 21.84, 267237, 1525, 6.084, 9.860, 11000, 1100, 11000, 1100),
    ('42CrMo4', 21.96, 402141, 4995, 3.928, 2.037, 1600, 800, 1600, 800);


ALTER TABLE materials ADD CONSTRAINT FK1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(id);

CREATE TABLE IF NOT EXISTS products (
  id INT PRIMARY KEY AUTO_INCREMENT,
  status ENUM('new', 'in_progress', 'ready', 'delivered') NOT NULL,
  name VARCHAR(255) NOT NULL,
  size DOUBLE NOT NULL,
  length DOUBLE NOT NULL,
  quality VARCHAR(255) NOT NULL,
  quantity INT NOT NULL,
  delivery_date DATE,
  delivery_number BIGINT
);

INSERT INTO products (status, name, quality, size, length, quantity) VALUES
    ('NEW', 'szegcsavar1', '25CrMo4', 12, 80, 23),
    ('NEW', 'szegcsavar2', '21CrMoV57', 16, 60, 10),
    ('NEW', 'szegcsavar anya3', '42CrMo4', 20, 75, 12),
    ('NEW', 'szegcsavar anya4', '25CrMo4', 20, 120, 20),
    ('NEW', 'menetes rúd5', '1.21CrMoV57', 24, 150, 60),
    ('NEW', 'menetes rúd6', '42CrMo4', 24, 110, 35);


CREATE TABLE IF NOT EXISTS material_product (
  material_id INT NOT NULL,
  product_id INT NOT NULL,
  PRIMARY KEY (material_id, product_id),
  FOREIGN KEY (material_id) REFERENCES materials(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE IF NOT EXISTS order_product (
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);
