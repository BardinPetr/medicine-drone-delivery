-- liquibase formatted sql

-- changeset artem:fun_update_route failOnError:true splitStatements:false
DROP TRIGGER IF EXISTS set_route_id_on_task_creation ON flight_task;

CREATE OR REPLACE FUNCTION update_route_id_on_task_creation()
    RETURNS TRIGGER AS
$$
DECLARE
    v_route_id BIGINT;
BEGIN
    IF NEW.status <> 'PACKING' THEN
        RETURN NEW;
    END IF;

    raise notice 'update_route_id_on_task_creation';

    -- Find a route with the same medical_facility_id and warehouse_id
    SELECT id
    INTO v_route_id
    FROM route
    WHERE medical_facility_id = NEW.medical_facility_id
      AND warehouse_id = NEW.warehouse_id
    LIMIT 1;

    -- Set the route_id if a matching route is found
    IF found THEN
        UPDATE flight_task
        SET route_id = v_route_id,
            status = 'READY'
        WHERE flight_task.id = NEW.id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_route_id_on_task_creation
    AFTER UPDATE OF warehouse_id
    ON flight_task
    FOR EACH ROW
EXECUTE PROCEDURE update_route_id_on_task_creation();
