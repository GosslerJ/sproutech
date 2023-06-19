-- Create products table
CREATE TABLE products (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  product_code VARCHAR(255),
  product_version_from INTEGER,
  product_version_to INTEGER
);

-- Create packages table
CREATE TABLE packages (
 id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  uuid VARCHAR(255),
  package_code VARCHAR(255),
  package_name VARCHAR(255),
  package_type VARCHAR(255),
  product_id INTEGER REFERENCES products (id)
);

-- Create levels table
CREATE TABLE levels (
 id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  level_code VARCHAR(255)
);

-- Create covers table
CREATE TABLE covers (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  cover_code VARCHAR(255),
  is_mandatory BOOLEAN,
  is_premium_free BOOLEAN
);

-- Create perils table
CREATE TABLE perils (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  peril_code VARCHAR(255),
  uuid VARCHAR(255),
  cover_id INTEGER REFERENCES covers (id)
);

-- Create limits table
CREATE TABLE limits (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  uuid VARCHAR(255),
  limit_min INTEGER,
  limit_max INTEGER,
  limit_range INTEGER,
  limit_amount DECIMAL,
  limit_days BIGINT,
  limit_text VARCHAR(255),
  cover_id INTEGER REFERENCES covers (id),
  peril_id INTEGER REFERENCES perils (id)
);

-- Create ibjects table
CREATE TABLE ibjects (
 id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  ibject_code VARCHAR(255),
  ibject_kind VARCHAR(255),
  max_number_of_ibjects INTEGER,
  is_mandatory BOOLEAN,
  level_id INTEGER REFERENCES levels (id)
);

-- Create package_level table
CREATE TABLE package_level (
  package_id INTEGER REFERENCES packages (id),
  level_id INTEGER REFERENCES levels (id),
  PRIMARY KEY (package_id, level_id)
);

-- Create cover_level table
CREATE TABLE cover_level (
  cover_id INTEGER REFERENCES covers (id),
  level_id INTEGER REFERENCES levels (id),
  PRIMARY KEY (cover_id, level_id)
);

---- Create indexes
--CREATE INDEX idx_package_product_id ON packages (product_id);
--CREATE INDEX idx_peril_cover_id ON perils (cover_id);
--CREATE INDEX idx_limit_cover_id ON limits (cover_id);
--CREATE INDEX idx_limit_peril_id ON limits (peril_id);
--CREATE INDEX idx_ibject_level_id ON ibjects (level_id);