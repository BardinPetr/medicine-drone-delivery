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

TRUNCATE TABLE warehouse_products CASCADE;
TRUNCATE TABLE flight_task CASCADE;

INSERT INTO warehouse_products (quantity, product_id, warehouse_id)
VALUES (100, 1, 1),
       (200, 1, 2),
       (300, 2, 2);

SELECT *
FROM flight_task;
SELECT *
FROM warehouse_products;

INSERT INTO flight_task (request_id, status, product_type_id, quantity)
VALUES (1, 'QUEUED', 1, 20),
       (1, 'QUEUED', 2, 30),
       (1, 'QUEUED', 3, 40),
       (1, 'QUEUED', 1, 60),
       (1, 'QUEUED', 2, 80),
       (1, 'QUEUED', 1, 800);

SELECT *
FROM flight_task;
SELECT *
FROM warehouse_products;

UPDATE warehouse_products
SET quantity = 800
WHERE product_id = 1
  AND warehouse_id = 1;

SELECT *
FROM flight_task;
SELECT *
FROM warehouse_products;

