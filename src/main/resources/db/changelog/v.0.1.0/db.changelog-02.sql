-- liquibase formatted sql

-- changeset petr:add_ftask_timestamp
ALTER TABLE flight_task
    ADD timestamp TIMESTAMP DEFAULT NOW();

-- changeset petr:add_ftask_timestamp_param
ALTER TABLE flight_task
    ALTER COLUMN timestamp SET NOT NULL;
