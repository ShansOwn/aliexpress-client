CREATE TABLE search_info (
  id BIGINT NOT NULL PRIMARY KEY,
  version INTEGER NOT NULL
);

CREATE TABLE ali_category (
  id BIGINT NOT NULL PRIMARY KEY,
  name VARCHAR(255)
);

CREATE TABLE ali_product (
  id BIGINT NOT NULL PRIMARY KEY,
  title VARCHAR(600),
  product_url VARCHAR(255),
  promotion_url VARCHAR(600),
  image_url VARCHAR(255),
  valid_time TIMESTAMP,
  evaluate_score REAL,
  original_price REAL,
  sale_price REAL,
  discount INTEGER,
  updated TIMESTAMP
);

CREATE TABLE ali_category_ali_product (
  category_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  CONSTRAINT FK_ALI_CATEGORY_ALI_PRODUCT FOREIGN KEY (category_id) REFERENCES ali_category (id),
  CONSTRAINT FK_ALI_PRODUCT_ALI_CATEGORY FOREIGN KEY (product_id) REFERENCES ali_product (id)
);

CREATE TABLE ali_api_track (
  method VARCHAR(255) NOT NULL PRIMARY KEY,
  requests INTEGER NOT NULL DEFAULT 0,
  last_update TIMESTAMP
);

INSERT INTO ali_category VALUES
  (3, 'Apparel & Accessories'),
  (34, 'Automobiles & Motorcycles'),
  (66, 'Beauty & Health'),
  (7, 'Computer & Office'),
  (13, 'Home Improvement'),
  (44, 'Consumer Electronics'),
  (5, 'Electrical Equipment & Supplies'),
  (502, 'Electronic Components & Supplies'),
  (1503, 'Furniture'),
  (200003655, 'Hair & Accessories'),
  (42, 'Hardware'),
  (15, 'Home & Garden'),
  (6, 'Home Appliances'),
  (200001996, 'Industry & Business'),
  (36, 'Jewelry & Accessories'),
  (39, 'Lights & Lighting'),
  (1524, 'Luggage & Bags'),
  (1501, 'Mother & Kids'),
  (21, 'Office & School Supplies'),
  (509, 'Phones & Telecommunications'),
  (30, 'Security & Protection'),
  (322, 'Shoes'),
  (18, 'Sports & Entertainment'),
  (1420, 'Tools'),
  (26, 'Toys & Hobbies'),
  (1511, 'Watches'),
  (320, 'Weddings & Events');

