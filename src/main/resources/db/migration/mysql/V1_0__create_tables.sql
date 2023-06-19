CREATE TABLE IF NOT EXISTS admins (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

ALTER TABLE admins ADD CONSTRAINT id UNIQUE (id);

CREATE TABLE products (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  product_code VARCHAR(255),
  product_version_from INTEGER,
  product_version_to INTEGER
);

CREATE TABLE packages (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  uuid VARCHAR(255),
  package_code VARCHAR(255),
  package_type VARCHAR(255),
  product_id INTEGER REFERENCES products(id)
);

CREATE TABLE levels (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  level_code VARCHAR(255)
);

CREATE TABLE covers (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  cover_code VARCHAR(255),
  is_mandatory BOOLEAN,
  is_premium_free BOOLEAN
);

CREATE TABLE perils (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  peril_code VARCHAR(255),
  uuid VARCHAR(255),
  cover_id INTEGER REFERENCES covers(id)
);

CREATE TABLE limits (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  uuid VARCHAR(255),
  limit_type VARCHAR(255),
  limit_min INTEGER,
  limit_max INTEGER,
  limit_range INTEGER,
  limit_amount DECIMAL,
  limit_days BIGINT,
  is_visible BOOLEAN,
  cover_id INTEGER REFERENCES covers(id),
  peril_id INTEGER REFERENCES perils(id)
);

CREATE TABLE ibjects (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  ibject_code VARCHAR(255),
  max_number_of_ibjects INTEGER,
  is_mandatory BOOLEAN,
  level_id INTEGER REFERENCES levels(id)
);

CREATE TABLE package_level (
  package_id INTEGER REFERENCES packages(id),
  level_id INTEGER REFERENCES levels(id),
  PRIMARY KEY (package_id, level_id)
);

CREATE TABLE cover_level (
  cover_id INTEGER REFERENCES covers(id),
  level_id INTEGER REFERENCES levels(id),
  PRIMARY KEY (cover_id, level_id)
);


