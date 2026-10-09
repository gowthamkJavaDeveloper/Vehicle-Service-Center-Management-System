CREATE TABLE vehicles (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    registration_number VARCHAR(20) NOT NULL UNIQUE,
    brand VARCHAR(80) NOT NULL,
    model VARCHAR(80) NOT NULL,
    manufacture_year INT,
    customer_id BIGINT NOT NULL
);

CREATE TABLE mechanics (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE
);

CREATE TABLE service_catalog (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(120) NOT NULL UNIQUE,
    description VARCHAR(255),
    base_price DECIMAL(12,2) NOT NULL,
    estimated_minutes INT NOT NULL
);

CREATE TABLE spare_parts (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sku VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(120) NOT NULL,
    unit_price DECIMAL(12,2) NOT NULL,
    stock_quantity INT NOT NULL,
    reorder_level INT NOT NULL
);

ALTER TABLE vehicles ADD CONSTRAINT fk_vehicles_customer FOREIGN KEY (customer_id) REFERENCES customers(id);
