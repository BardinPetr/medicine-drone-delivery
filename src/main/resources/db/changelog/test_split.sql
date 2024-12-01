TRUNCATE TABLE product_type CASCADE;
INSERT INTO product_type (type)
VALUES ('Product A'),
       ('Product B'),
       ('Product C');

TRUNCATE TABLE warehouse CASCADE;
INSERT INTO warehouse (name, lat, lon)
VALUES ('Warehouse 1', 59.9373, 30.2085),
       ('Warehouse 2', 59.9799, 30.4028),
       ('Warehouse 3', 59.9105, 30.3534);

TRUNCATE TABLE medical_facility CASCADE;
INSERT INTO medical_facility (name, responsible_user_id, lat, lon)
VALUES ('Pharmacy', 1, 59.9282, 30.3023),
       ('Hospital', 2, 60.0242, 30.2841);

TRUNCATE TABLE type_of_drone CASCADE;
INSERT INTO type_of_drone (name, max_weight, speed)
VALUES ('TYP1', 5, 0.0005),
       ('TYP2', 2, 0.001);

TRUNCATE TABLE flight_task CASCADE;
TRUNCATE TABLE drone CASCADE;
INSERT INTO drone (model_id, lat, lon, status)
VALUES (1, 0.0, 0.0, 'IDLE'),
       (1, 0.0, 0.0, 'IDLE'),
       (2, 0.0, 0.0, 'IDLE'),
       (2, 0.0, 0.0, 'IDLE');

TRUNCATE TABLE warehouse_products CASCADE;
INSERT INTO warehouse_products (quantity, product_id, warehouse_id)
VALUES (10, 1, 1),
       (10, 2, 1),
       (20, 1, 2),
       (20, 2, 2),
       (30, 3, 2);


insert into no_flight_zone (id, radius, lat, lon)
values  (7, 0.005, 59.9081, 30.2593),
        (19, 0.01, 59.9216, 30.327),
        (14, 0.01, 59.9351, 30.2494),
        (4, 0.01, 59.9385, 30.33),
        (13, 0.01, 59.9473, 30.3733),
        (11, 0.005, 59.952, 30.2026),
        (9, 0.005, 59.9536, 30.2799),
        (15, 0.01, 59.967, 30.2332),
        (3, 0.005, 59.9688, 30.3749),
        (18, 0.005, 59.9703, 30.2727);


-- INSERT INTO request (user_id, status, medical_facility_id)
-- VALUES (1, 'QUEUED', 1);

-- INSERT INTO request (user_id, status, medical_facility_id)
-- VALUES (1, 'QUEUED', 1),
--        (1, 'QUEUED', 1);

-- INSERT INTO route (warehouse_id, medical_facility_id)
-- VALUES (1, 1),
--        (2, 1);


--
-- INSERT INTO warehouse_products (quantity, product_id, warehouse_id)
-- VALUES (10, 1, 1),
--        (20, 1, 2),
--        (30, 2, 2);

-- INSERT INTO drone (model_id, lat, lon, status)
-- VALUES (1, 0.0, 0.0, 'IDLE'),
--        (1, 0.0, 0.0, 'IDLE'),
--        (2, 0.0, 0.0, 'IDLE'),
--        (2, 0.0, 0.0, 'IDLE');

-- INSERT INTO route_point(point_number, lat, lon, route_id)
-- VALUES (0, 0, 0, 1),
--        (1, 5, 5, 1),
--        (2, 10, 15, 1),
--        (0, 0, 0, 2),
--        (1, -10, 15, 2),
--        (2, -10, 15, 2);


-- INSERT INTO request_entry (request_id, product_type_id, quantity)
-- VALUES (1, 1, 4),
--        (1, 2, 8),
--        (2, 1, 10);
--
-- UPDATE drone
--     SET status = 'IDLE'
--     WHERE id < 3;


-- SELECT *
-- FROM flight_task;
-- SELECT *
-- FROM warehouse_products;
--
-- UPDATE warehouse_products
-- SET quantity = 800
-- WHERE product_id = 1
--   AND warehouse_id = 1;
--
-- SELECT *
-- FROM flight_task;
-- SELECT *
-- FROM warehouse_products;

