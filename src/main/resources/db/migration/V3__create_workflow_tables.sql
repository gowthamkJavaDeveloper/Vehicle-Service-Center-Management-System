CREATE TABLE bookings (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 vehicle_id BIGINT NOT NULL, service_id BIGINT NOT NULL, mechanic_id BIGINT NULL,
 appointment_at DATETIME NOT NULL, status VARCHAR(25) NOT NULL DEFAULT 'SCHEDULED',
 notes VARCHAR(500), created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_booking_vehicle FOREIGN KEY(vehicle_id) REFERENCES vehicles(id),
 CONSTRAINT fk_booking_service FOREIGN KEY(service_id) REFERENCES service_catalog(id),
 CONSTRAINT fk_booking_mechanic FOREIGN KEY(mechanic_id) REFERENCES mechanics(id),
 INDEX idx_booking_time (appointment_at), INDEX idx_booking_mechanic_time(mechanic_id, appointment_at)
);
CREATE TABLE job_cards (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, booking_id BIGINT NOT NULL UNIQUE,
 complaint VARCHAR(1000) NOT NULL, diagnosis VARCHAR(1000), work_notes VARCHAR(2000),
 labor_charge DECIMAL(12,2) NOT NULL DEFAULT 0.00,
 status VARCHAR(25) NOT NULL DEFAULT 'OPEN',
 created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_job_booking FOREIGN KEY(booking_id) REFERENCES bookings(id)
);
CREATE TABLE stock_movements (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, part_id BIGINT NOT NULL,
 job_card_id BIGINT NULL, movement_type VARCHAR(12) NOT NULL, quantity INT NOT NULL,
 remarks VARCHAR(255), created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_movement_part FOREIGN KEY(part_id) REFERENCES spare_parts(id),
 CONSTRAINT fk_movement_job FOREIGN KEY(job_card_id) REFERENCES job_cards(id)
);
CREATE TABLE invoices (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, job_card_id BIGINT NOT NULL UNIQUE,
 invoice_number VARCHAR(40) NOT NULL UNIQUE, subtotal DECIMAL(12,2) NOT NULL,
 tax_amount DECIMAL(12,2) NOT NULL, total_amount DECIMAL(12,2) NOT NULL,
 paid_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
 status VARCHAR(25) NOT NULL DEFAULT 'UNPAID', created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_invoice_job FOREIGN KEY(job_card_id) REFERENCES job_cards(id)
);
CREATE TABLE payments (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, invoice_id BIGINT NOT NULL,
 amount DECIMAL(12,2) NOT NULL, method VARCHAR(25) NOT NULL, reference_number VARCHAR(100),
 paid_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_payment_invoice FOREIGN KEY(invoice_id) REFERENCES invoices(id)
);
CREATE TABLE app_users (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 username VARCHAR(80) NOT NULL UNIQUE, password_hash VARCHAR(100) NOT NULL,
 role VARCHAR(30) NOT NULL, enabled BOOLEAN NOT NULL DEFAULT TRUE
);
