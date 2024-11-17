CREATE OR REPLACE FUNCTION random_latitude()
    RETURNS DOUBLE PRECISION AS
$$
BEGIN
    RETURN (random() * (60.0 - 59.8)) + 59.8;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION random_longitude()
    RETURNS DOUBLE PRECISION AS
$$
BEGIN
    RETURN (random() * (30.7 - 30.1)) + 30.1;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE generate_demo_entities()
AS
$$
DECLARE
    i                   INTEGER;
    j                   INTEGER;
    user_id             BIGINT;
    medical_facility_id BIGINT;
    request_id          BIGINT;
    product_type_id     BIGINT;
    warehouse_id        BIGINT;
    flight_task_id      BIGINT;
    drone_id            BIGINT;
BEGIN
    FOR i IN 1..10
        LOOP
            INSERT INTO "user" (username, password_hash, role)
            VALUES (CONCAT('user', i), 'password_hash', 'USER')
            RETURNING id INTO user_id;

            INSERT INTO medical_facility (name, responsible_user_id, lat, lon)
            VALUES (CONCAT('Medical Facility ', i), user_id, random_latitude(), random_longitude())
            RETURNING id INTO medical_facility_id;

            INSERT INTO request (user_id, status, medical_facility_id)
            VALUES (user_id, 'QUEUED', medical_facility_id)
            RETURNING id INTO request_id;

            FOR j IN 1..(random() * 3 + 2)
                LOOP
                    INSERT INTO product_type (type, piece_weight)
                    VALUES (CONCAT('Product Type ', j), random() * 10 + 1)
                    RETURNING id INTO product_type_id;

                    INSERT INTO request_entry (request_id, product_type_id, quantity)
                    VALUES (request_id, product_type_id, random() * 10 + 1);
                END LOOP;

            INSERT INTO warehouse (name, lat, lon)
            VALUES (CONCAT('Warehouse ', i), random_latitude(), random_longitude())
            RETURNING id INTO warehouse_id;

            INSERT INTO warehouse_products (quantity, reserved_quantity, product_id, warehouse_id)
            VALUES (random() * 100 + 20, random() * 5 + 1, product_type_id, warehouse_id);

            INSERT INTO flight_task (request_id, status, product_type_id, warehouse_id, medical_facility_id, quantity)
            VALUES (request_id, 'QUEUED', product_type_id, warehouse_id, medical_facility_id, random() * 10 + 1)
            RETURNING id INTO flight_task_id;

            INSERT INTO type_of_drone (name, max_weight)
            VALUES (CONCAT('Type of Drone ', i), random() * 100 + 1);

            INSERT INTO drone (model_id, status, flight_task_id, lat, lon)
            VALUES (i, 'IDLE', flight_task_id, random_latitude(), random_longitude())
            RETURNING id INTO drone_id;

            INSERT INTO no_flight_zone (radius, lat, lon)
            VALUES (random() * 10 + 1, random_latitude(), random_longitude());

            INSERT INTO route_point (flight_task_id, point_number, lat, lon)
            VALUES (flight_task_id, 1, random_latitude(), random_longitude());
        END LOOP;
END;
$$ LANGUAGE plpgsql;

CALL generate_demo_entities();
