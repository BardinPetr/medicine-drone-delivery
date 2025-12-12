-- liquibase formatted sql

-- changeset petr:remove_triggers_moved_to_kotlin failOnError:true splitStatements:false

DROP TRIGGER IF EXISTS trigger_create_flight_task_on_drone_idle ON drone;
DROP TRIGGER IF EXISTS trigger_create_flight_task ON request_entry;
DROP TRIGGER IF EXISTS update_flight_task_on_creation_trigger ON flight_task;
DROP TRIGGER IF EXISTS update_queued_flight_tasks_on_warehouse_update_trigger ON warehouse_products;
DROP TRIGGER IF EXISTS set_route_id_on_task_creation ON flight_task;

-- changeset petr:remove_functions_moved_to_kotlin failOnError:true splitStatements:false

DROP FUNCTION IF EXISTS fun_trigger_create_flight_task_on_drone_idle;
DROP FUNCTION IF EXISTS fun_trigger_create_flight_task;
DROP FUNCTION IF EXISTS create_flight_task;

DROP FUNCTION IF EXISTS assign_flight_task_to_warehouse_product;
DROP FUNCTION IF EXISTS assign_flight_task_on_creation;
DROP FUNCTION IF EXISTS assign_flight_tasks_on_warehouse_update;
DROP FUNCTION IF EXISTS update_route_id_on_task_creation;

