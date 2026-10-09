CREATE TABLE customers (
    id BIGINT NOT NULL AUTO_INCREMENT,
    customer_code VARCHAR(30) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    address VARCHAR(255),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
        ON UPDATE CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_customers PRIMARY KEY (id),
    CONSTRAINT uk_customers_code UNIQUE (customer_code),
    CONSTRAINT uk_customers_email UNIQUE (email),
    CONSTRAINT uk_customers_phone UNIQUE (phone_number)
);