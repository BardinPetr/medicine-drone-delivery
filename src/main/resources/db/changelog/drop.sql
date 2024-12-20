ALTER TABLE drone DROP CONSTRAINT IF EXISTS FK_DRONE_ON_FLIGHT_TASK;
ALTER TABLE drone DROP CONSTRAINT IF EXISTS FK_DRONE_ON_MODEL;

ALTER TABLE flight_task DROP CONSTRAINT IF EXISTS FK_FLIGHTTASK_ON_MEDICAL_FACILITY;
ALTER TABLE flight_task DROP CONSTRAINT IF EXISTS FK_FLIGHTTASK_ON_PRODUCT_TYPE;
ALTER TABLE flight_task DROP CONSTRAINT IF EXISTS FK_FLIGHTTASK_ON_REQUEST;
ALTER TABLE flight_task DROP CONSTRAINT IF EXISTS FK_FLIGHTTASK_ON_WAREHOUSE;

ALTER TABLE medical_facility DROP CONSTRAINT IF EXISTS FK_MEDICALFACILITY_ON_RESPONSIBLE_USER;

ALTER TABLE request_entry DROP CONSTRAINT IF EXISTS FK_REQUESTENTRY_ON_PRODUCT_TYPE;
ALTER TABLE request_entry DROP CONSTRAINT IF EXISTS FK_REQUESTENTRY_ON_REQUEST;

ALTER TABLE request DROP CONSTRAINT IF EXISTS FK_REQUEST_ON_MEDICAL_FACILITY;
ALTER TABLE request DROP CONSTRAINT IF EXISTS FK_REQUEST_ON_USER;

ALTER TABLE route_point DROP CONSTRAINT IF EXISTS FK_ROUTEPOINT_ON_FLIGHT_TASK;

ALTER TABLE user_audit DROP CONSTRAINT IF EXISTS FK_USER_AUDIT_ON_REVISION;

ALTER TABLE warehouse_products DROP CONSTRAINT IF EXISTS FK_WAREHOUSEPRODUCTS_ON_PRODUCT;
ALTER TABLE warehouse_products DROP CONSTRAINT IF EXISTS FK_WAREHOUSEPRODUCTS_ON_WAREHOUSE;

DROP TABLE IF EXISTS drone;
DROP TABLE IF EXISTS flight_task;
DROP TABLE IF EXISTS medical_facility;
DROP TABLE IF EXISTS no_flight_zone;
DROP TABLE IF EXISTS product_type;
DROP TABLE IF EXISTS request;
DROP TABLE IF EXISTS request_entry;
DROP TABLE IF EXISTS rev_info;
DROP TABLE IF EXISTS route_point;
DROP TABLE IF EXISTS type_of_drone;
DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS user_audit;
DROP TABLE IF EXISTS warehouse;
DROP TABLE IF EXISTS warehouse_products;
