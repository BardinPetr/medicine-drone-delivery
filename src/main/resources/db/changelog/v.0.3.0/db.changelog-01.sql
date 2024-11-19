-- liquibase formatted sql

-- changeset petr:add_drone_speed
ALTER TABLE type_of_drone
    ADD speed DOUBLE PRECISION DEFAULT 0 NOT NULL;

-- changeset petr:fix_weight
ALTER TABLE product_type
    DROP COLUMN piece_weight;

ALTER TABLE type_of_drone
    DROP COLUMN max_weight;

ALTER TABLE type_of_drone
    ADD max_weight BIGINT NOT NULL default 1;
