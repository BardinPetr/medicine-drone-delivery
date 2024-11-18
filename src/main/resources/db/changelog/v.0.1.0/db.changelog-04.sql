-- liquibase formatted sql

-- changeset petr:request_entry_add_fulfilled
ALTER TABLE request_entry
    ADD fulfilled_quantity INTEGER DEFAULT 0;

-- changeset petr:request_entry_chk_fulfilled
ALTER TABLE request_entry
    ALTER COLUMN fulfilled_quantity SET NOT NULL,
    ADD CONSTRAINT chk_fulfilled_quantity CHECK (fulfilled_quantity < quantity AND fulfilled_quantity >= 0);
