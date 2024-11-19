-- liquibase formatted sql

-- changeset petr:fun_assign_ft_to_wh failOnError:true splitStatements:false
CREATE OR REPLACE FUNCTION assign_flight_task_to_warehouse_product(
    ft flight_task,
    wp warehouse_products
)
    RETURNS VOID AS
$$
BEGIN
    raise notice 'assign_flight_task_to_warehouse_product';

    IF wp.quantity < ft.quantity THEN
        RAISE EXCEPTION 'Insufficient quantity in warehouse';
    END IF;

    UPDATE flight_task
    SET status = 'PACKING'
    WHERE id = ft.id;

    UPDATE warehouse_products
    SET quantity = quantity - ft.quantity
    WHERE warehouse_id = wp.warehouse_id
      AND product_id = wp.product_id;

    UPDATE flight_task
    SET warehouse_id = wp.warehouse_id
    WHERE id = ft.id;
END;
$$ LANGUAGE plpgsql;

-- changeset petr:fun_assign_ft_to_wh_wrapper_new failOnError:true splitStatements:false
CREATE OR REPLACE FUNCTION assign_flight_task_on_creation()
    RETURNS TRIGGER AS
$$
DECLARE
    wp warehouse_products;
BEGIN
    raise notice 'assign_flight_task_on_creation';

    SELECT *
    INTO wp
    FROM warehouse_products
    WHERE product_id = NEW.product_type_id
      AND quantity >= NEW.quantity
    ORDER BY warehouse_id
    LIMIT 1;

    IF found THEN
        PERFORM assign_flight_task_to_warehouse_product(NEW, wp);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- changeset petr:fun_assign_ft_to_wh_wrapper_upd failOnError:true splitStatements:false
CREATE OR REPLACE FUNCTION assign_flight_tasks_on_warehouse_update()
    RETURNS TRIGGER AS
$$
DECLARE
    ft flight_task;
BEGIN
    raise notice 'assign_flight_tasks_on_warehouse_update';

    SELECT *
    INTO ft
    FROM flight_task
    WHERE status = 'QUEUED'
      AND product_type_id = NEW.product_id
      AND quantity <= NEW.quantity
    ORDER BY id
    LIMIT 1;

    IF found THEN
        PERFORM assign_flight_task_to_warehouse_product(ft, NEW);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- changeset petr:trig_assign_ft_to_wh_new failOnError:true splitStatements:false

DROP TRIGGER IF EXISTS update_flight_task_on_creation_trigger ON flight_task;
CREATE TRIGGER update_flight_task_on_creation_trigger
    AFTER INSERT
    ON flight_task
    FOR EACH ROW
EXECUTE PROCEDURE assign_flight_task_on_creation();

-- changeset petr:trig_assign_ft_to_wh_upd failOnError:true splitStatements:false

DROP TRIGGER IF EXISTS update_queued_flight_tasks_on_warehouse_update_trigger ON warehouse_products;
CREATE TRIGGER update_queued_flight_tasks_on_warehouse_update_trigger
    AFTER UPDATE OF quantity
    ON warehouse_products
    FOR EACH ROW
EXECUTE PROCEDURE assign_flight_tasks_on_warehouse_update();
