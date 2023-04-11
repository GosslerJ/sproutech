CREATE TABLE IF NOT EXISTS admins (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

ALTER TABLE admins ADD CONSTRAINT id UNIQUE (id);

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

ALTER TABLE customers ADD CONSTRAINT id UNIQUE (id);

CREATE TABLE IF NOT EXISTS orders (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    status ENUM('new', 'in_progress', 'done', 'delivered', 'cancelled') NOT NULL,
    order_date DATE,
    delivery_deadline DATE,
    delivery_date DATE,
    delivery_number BIGINT,
    customer_id INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS warehouses (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name ENUM('external', 'internal', 'bag', 'delivered') NOT NULL,
  zip_code VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  address VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS materials (
  id INT PRIMARY KEY AUTO_INCREMENT,
  quality VARCHAR(255) NOT NULL,
  size FLOAT NOT NULL,
  hit_number BIGINT NOT NULL,
  quantity INT NOT NULL,
  unit_price BIGINT,
  unit_length FLOAT NOT NULL,
  unit_weight FLOAT NOT NULL,
  total_length FLOAT,
  total_weight FLOAT,
  remaining_length FLOAT,
  remaining_weight FLOAT,
  updated_at DATE,
  warehouse_id INT,
  FOREIGN KEY (warehouse_id) REFERENCES warehouses(id)
);

CREATE TABLE IF NOT EXISTS products (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  size FLOAT NOT NULL,
  length FLOAT NOT NULL,
  quality VARCHAR(255) NOT NULL,
  quantity INT NOT NULL
);

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