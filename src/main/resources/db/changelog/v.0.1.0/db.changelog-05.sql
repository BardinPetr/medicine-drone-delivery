-- liquibase formatted sql

-- changeset petr:warehouse_products_rem_reserved
ALTER TABLE warehouse_products
    DROP COLUMN reserved_quantity CASCADE;

-- changeset petr:add_flight_task_enum
ALTER TABLE flight_task
    DROP CONSTRAINT IF EXISTS chk_flighttask_status;

ALTER TABLE flight_task
    ADD CONSTRAINT chk_flighttask_status CHECK (status IN ('QUEUED', 'PACKING', 'IN_PROGRESS', 'COMPLETED'));
