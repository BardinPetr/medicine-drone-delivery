-- liquibase formatted sql

-- changeset petr:del_route_trg
DROP TRIGGER IF EXISTS set_route_id_on_task_creation ON flight_task CASCADE ;

DROP FUNCTION update_route_id_on_task_creation CASCADE ;
