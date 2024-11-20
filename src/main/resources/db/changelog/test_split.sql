INSERT INTO product_type (type)
VALUES ('Product A'),
       ('Product B'),
       ('Product C');

INSERT INTO warehouse (name, lat, lon)
VALUES ('Warehouse 1', 0.0, 0.0),
       ('Warehouse 2', 1.0, 1.0),
       ('Warehouse 3', 2.0, 2.0);

INSERT INTO "user" (username, password_hash, role)
VALUES ('utest', '', 'USER');

INSERT INTO medical_facility (name, responsible_user_id, lat, lon)
VALUES ('test', 1, 1.1, 2.2);

INSERT INTO request (user_id, status, medical_facility_id)
VALUES (1, 'QUEUED', 1);

INSERT INTO type_of_drone (name, max_weight, speed)
VALUES ('1', 2, 0.001),
       ('2', 5, 0.0001);

INSERT INTO request (user_id, status, medical_facility_id)
VALUES (1, 'QUEUED', 1),
       (1, 'QUEUED', 1);

-- INSERT INTO route (warehouse_id, medical_facility_id)
-- VALUES (1, 1),
--        (2, 1);

TRUNCATE TABLE warehouse_products CASCADE;
TRUNCATE TABLE flight_task CASCADE;
TRUNCATE TABLE request_entry CASCADE;
TRUNCATE TABLE flight_task CASCADE;

INSERT INTO warehouse_products (quantity, product_id, warehouse_id)
VALUES (10, 1, 1),
       (20, 1, 2),
       (30, 2, 2);

INSERT INTO drone (model_id, lat, lon, status)
VALUES (1, 0.0, 0.0, 'IDLE'),
       (1, 0.0, 0.0, 'IDLE'),
       (2, 0.0, 0.0, 'IDLE'),
       (2, 0.0, 0.0, 'IDLE');

-- INSERT INTO route_point(point_number, lat, lon, route_id)
-- VALUES (0, 0, 0, 1),
--        (1, 5, 5, 1),
--        (2, 10, 15, 1),
--        (0, 0, 0, 2),
--        (1, -10, 15, 2),
--        (2, -10, 15, 2);



INSERT INTO request_entry (request_id, product_type_id, quantity)
VALUES (1, 1, 4),
       (1, 2, 8),
       (2, 1, 10);

-- UPDATE drone
--     SET status = 'FLYING_TO'
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

