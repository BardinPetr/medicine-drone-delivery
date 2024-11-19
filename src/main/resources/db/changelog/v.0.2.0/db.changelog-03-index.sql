-- liquibase formatted sql

-- changeset petr:add_index_re

CREATE INDEX idx_request_entry_unfulfilled_quantity
    ON request_entry USING btree ((quantity - fulfilled_quantity));

-- changeset petr:add_index_rp

CREATE INDEX idx_route_point_point_number_flight_task_id
    ON route_point (route_id, point_number);
