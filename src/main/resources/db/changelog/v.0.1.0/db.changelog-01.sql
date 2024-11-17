-- liquibase formatted sql

-- changeset petr:add_constraints_drone
ALTER TABLE drone
    ADD CONSTRAINT chk_drone_status CHECK (status IN ('IDLE', 'FLYING_TO', 'FLYING_FROM')),
    ADD CONSTRAINT chk_drone_lat CHECK (lat BETWEEN -90 AND 90),
    ADD CONSTRAINT chk_drone_lon CHECK (lon BETWEEN -180 AND 180);

-- changeset petr:add_constraints_flight_task
ALTER TABLE flight_task
    ADD CONSTRAINT chk_flighttask_status CHECK (status IN ('QUEUED', 'IN_PROGRESS', 'COMPLETED')),
    ADD CONSTRAINT chk_flighttask_quantity CHECK (quantity > 0);

-- changeset petr:add_constraints_flight_medical_facility
ALTER TABLE medical_facility
    ADD CONSTRAINT chk_medicalfacility_lat CHECK (lat BETWEEN -90 AND 90),
    ADD CONSTRAINT chk_medicalfacility_lon CHECK (lon BETWEEN -180 AND 180);

-- changeset petr:fix_medical_facility
ALTER TABLE medical_facility
    ALTER COLUMN responsible_user_id SET NOT NULL;

-- changeset petr:add_constraints_no_flight_zone
ALTER TABLE no_flight_zone
    ADD CONSTRAINT chk_noflightzone_radius CHECK (radius > 0),
    ADD CONSTRAINT chk_noflightzone_lat CHECK (lat BETWEEN -90 AND 90),
    ADD CONSTRAINT chk_noflightzone_lon CHECK (lon BETWEEN -180 AND 180);

-- changeset petr:add_constraints_product_type
ALTER TABLE product_type
    ADD CONSTRAINT chk_producttype_piece_weight CHECK (piece_weight > 0);

-- changeset petr:add_constraints_request
ALTER TABLE request
    ADD CONSTRAINT chk_request_status CHECK (status IN ('QUEUED', 'IN_PROGRESS', 'COMPLETED'));

-- changeset petr:add_constraints_request_entry
ALTER TABLE request_entry
    ADD CONSTRAINT chk_requestentry_quantity CHECK (quantity > 0);

-- changeset petr:add_constraints_route_point
ALTER TABLE route_point
    ADD CONSTRAINT chk_routepoint_lat CHECK (lat BETWEEN -90 AND 90),
    ADD CONSTRAINT chk_routepoint_lon CHECK (lon BETWEEN -180 AND 180);

-- changeset petr:add_constraints_type_of_drone
ALTER TABLE type_of_drone
    ADD CONSTRAINT chk_typeofdrone_max_weight CHECK (max_weight > 0);

-- changeset petr:add_constraints_user
ALTER TABLE "user"
    ADD CONSTRAINT chk_user_role CHECK (role IN ('ADMIN', 'MEDIC', 'WAREHOUSE', 'USER'));

-- changeset petr:add_constraints_warehouse
ALTER TABLE warehouse
    ADD CONSTRAINT chk_warehouse_lat CHECK (lat BETWEEN -90 AND 90),
    ADD CONSTRAINT chk_warehouse_lon CHECK (lon BETWEEN -180 AND 180);

-- changeset petr:add_constraints_warehouse_products
ALTER TABLE warehouse_products
    ADD CONSTRAINT chk_warehouseproducts_quantity CHECK (quantity >= 0),
    ADD CONSTRAINT chk_warehouseproducts_reserved_quantity CHECK (reserved_quantity >= 0 AND reserved_quantity <= quantity);
