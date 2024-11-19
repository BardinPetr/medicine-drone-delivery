-- liquibase formatted sql

-- changeset artem:fun_create_ft failOnError:true splitStatements:false
CREATE OR REPLACE FUNCTION create_flight_task(
    p_drone_id BIGINT,
    p_request_entry_id BIGINT
)
    RETURNS BIGINT AS
$$
DECLARE
    v_drone_capacity FLOAT;
    v_request_entry  RECORD;
    v_quantity       INTEGER;
    v_new_task_id    BIGINT;
BEGIN
    raise notice 'create_flight_task';

    -- Find drone capacity using type_of_drone
    SELECT t.max_weight
    INTO v_drone_capacity
    FROM drone d
             JOIN type_of_drone t ON d.model_id = t.id
    WHERE d.id = p_drone_id;

    -- Get request_entry
    SELECT *
    INTO v_request_entry
    FROM request_entry
    WHERE id = p_request_entry_id;

    -- Calculate quantity
    v_quantity := LEAST(v_drone_capacity, v_request_entry.quantity - v_request_entry.fulfilled_quantity);

    UPDATE request_entry
    SET fulfilled_quantity = fulfilled_quantity + v_quantity
    WHERE id = p_request_entry_id;

    -- Create a new task
    INSERT INTO flight_task (request_id,
                             status,
                             product_type_id,
                             warehouse_id,
                             medical_facility_id,
                             quantity)
    VALUES (v_request_entry.request_id,
            'QUEUED',
            v_request_entry.product_type_id,
            NULL,
            (SELECT medical_facility_id FROM request WHERE id = v_request_entry.request_id),
            v_quantity)
    RETURNING id INTO v_new_task_id;

    UPDATE drone
    SET flight_task_id = v_new_task_id,
        status         = 'READY'
    WHERE drone.id = p_drone_id;

    RETURN v_new_task_id;
END;
$$ LANGUAGE plpgsql;

-- changeset artem:fun_create_ft_on_rq_ins failOnError:true splitStatements:false
DROP TRIGGER IF EXISTS trigger_create_flight_task ON request_entry;

CREATE OR REPLACE FUNCTION fun_trigger_create_flight_task()
    RETURNS TRIGGER AS
$$
DECLARE
    v_drone_id    BIGINT;
    left_quantity BIGINT;
BEGIN
    LOOP
        SELECT (quantity - fulfilled_quantity)
        INTO left_quantity
        FROM request_entry
        WHERE id = NEW.id;

        IF left_quantity = 0 THEN
            EXIT;
        END IF;

        raise notice 'fun_trigger_create_flight_task';

        -- Find a free drone
        SELECT drone.id
        INTO v_drone_id
        FROM drone
                 LEFT JOIN type_of_drone tod on tod.id = drone.model_id
        WHERE status = 'IDLE'
        ORDER BY tod.max_weight DESC
        LIMIT 1;

        IF FOUND THEN
            PERFORM create_flight_task(v_drone_id, NEW.id);
        ELSE
            EXIT;
        END IF;

    END LOOP;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_create_flight_task
    AFTER INSERT
    ON request_entry
    FOR EACH ROW
EXECUTE PROCEDURE fun_trigger_create_flight_task();

-- changeset artem:fun_create_ft_on_idle failOnError:true splitStatements:false
DROP TRIGGER IF EXISTS trigger_create_flight_task_on_drone_idle ON drone;

CREATE OR REPLACE FUNCTION fun_trigger_create_flight_task_on_drone_idle()
    RETURNS TRIGGER AS
$$
DECLARE
    v_request_entry_id BIGINT;
    v_drone_capacity   FLOAT;
BEGIN
    raise notice 'fun_trigger_create_flight_task_on_drone_idle';

    -- Find the drone's capacity
    SELECT t.max_weight
    INTO v_drone_capacity
    FROM drone d
             JOIN type_of_drone t ON d.model_id = t.id
    WHERE d.id = NEW.id;

    -- Find a free request entry
    SELECT id
    INTO v_request_entry_id
    FROM request_entry
    WHERE (quantity - fulfilled_quantity) > 0
    LIMIT 1;

    IF FOUND THEN
        -- Create a new flight task
        PERFORM create_flight_task(NEW.id, v_request_entry_id);
    ELSE
        RAISE NOTICE 'No free request entries available';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_create_flight_task_on_drone_idle
    AFTER UPDATE OF status
    ON drone
    FOR EACH ROW
    WHEN (NEW.status = 'IDLE')
EXECUTE PROCEDURE fun_trigger_create_flight_task_on_drone_idle();
