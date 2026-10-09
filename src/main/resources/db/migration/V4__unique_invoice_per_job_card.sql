
ALTER TABLE invoices
ADD CONSTRAINT uk_invoices_job_card_id
UNIQUE (job_card_id);
