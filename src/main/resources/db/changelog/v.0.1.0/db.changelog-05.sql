-- liquibase formatted sql

-- changeset petr:warehouse_products_rem_reserved
ALTER TABLE warehouse_products
    DROP COLUMN reserved_quantity CASCADE;

-- changeset petr:add_flight_task_enum
ALTER TABLE flight_task
    DROP CONSTRAINT IF EXISTS chk_flighttask_status;

ALTER TABLE flight_task
    ADD CONSTRAINT chk_flighttask_status CHECK (status IN ('QUEUED', 'READY', 'PACKING', 'IN_PROGRESS', 'COMPLETED'));

-- changeset petr:upd_drone_status

ALTER TABLE drone
    DROP CONSTRAINT IF EXISTS chk_drone_status;

ALTER TABLE drone
    ADD CONSTRAINT chk_drone_status CHECK (status IN ('IDLE', 'READY', 'FLYING_TO', 'FLYING_FROM'));
