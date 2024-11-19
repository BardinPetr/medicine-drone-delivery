INSERT INTO product_type (type, piece_weight)
VALUES ('Product A', 1.0),
       ('Product B', 2.0),
       ('Product C', 3.0);

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

INSERT INTO type_of_drone (name, max_weight)
VALUES ('1', 2),
       ('2', 5);

INSERT INTO request (user_id, status, medical_facility_id)
VALUES (1, 'QUEUED', 1),
       (1, 'QUEUED', 1);

INSERT INTO route (warehouse_id, medical_facility_id)
VALUES (1, 1),
       (2, 1);

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

INSERT INTO request_entry (request_id, product_type_id, quantity)
VALUES (1, 1, 4),
       (1, 2, 8),
       (2, 1, 10);

UPDATE drone
    SET status = 'IDLE'
WHERE id < 10

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

