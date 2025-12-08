-- liquibase formatted sql

-- changeset petr:check_location_in_zone_fix_distance_calc failOnError:true splitStatements:false

DROP TRIGGER IF EXISTS check_medical_facility_zone_trigger ON medical_facility;
DROP TRIGGER IF EXISTS check_warehouse_zone_trigger ON warehouse;
DROP FUNCTION IF EXISTS check_location_in_zone;

CREATE OR REPLACE FUNCTION check_location_in_zone()
    RETURNS TRIGGER AS
$$
DECLARE
    zone     RECORD;
    distance DOUBLE PRECISION;
BEGIN
    FOR zone IN SELECT * FROM no_flight_zone
        LOOP
            distance := gps_distance(NEW.lat, NEW.lon, zone.lat, zone.lon);
            IF distance < zone.radius THEN
                IF TG_TABLE_NAME = 'warehouse' THEN
                    RAISE EXCEPTION 'Warehouse is within a no flight zone';
                ELSEIF TG_TABLE_NAME = 'medical_facility' THEN
                    RAISE EXCEPTION 'Medical facility is within a no flight zone';
                END IF;
            END IF;
        END LOOP;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- changeset petr:check_location_in_zone_fix_triggers failOnError:true splitStatements:false

CREATE TRIGGER check_warehouse_zone_trigger
    BEFORE INSERT
    ON warehouse
    FOR EACH ROW
EXECUTE FUNCTION check_location_in_zone();

CREATE TRIGGER check_medical_facility_zone_trigger
    BEFORE INSERT
    ON medical_facility
    FOR EACH ROW
EXECUTE FUNCTION check_location_in_zone();


-- changeset petr:check_no_flight_zone_fix_distance_calc failOnError:true splitStatements:false

DROP TRIGGER IF EXISTS check_no_flight_zone_trigger ON no_flight_zone;
DROP FUNCTION IF EXISTS check_no_flight_zone;

CREATE OR REPLACE FUNCTION check_no_flight_zone()
    RETURNS TRIGGER AS $$
DECLARE
    facility RECORD;
    warehouse RECORD;
    distance DOUBLE PRECISION;
BEGIN
    -- Check each medical facility
    FOR facility IN SELECT * FROM medical_facility LOOP
            distance := gps_distance(NEW.lat, NEW.lon, facility.lat, facility.lon);
            IF distance < NEW.radius THEN
                RAISE EXCEPTION 'No flight zone includes a medical facility';
            END IF;
        END LOOP;

    -- Check each warehouse
    FOR warehouse IN SELECT * FROM warehouse LOOP
            distance := gps_distance(NEW.lat, NEW.lon, warehouse.lat, warehouse.lon);
            IF distance < NEW.radius THEN
                RAISE EXCEPTION 'No flight zone includes a warehouse';
            END IF;
        END LOOP;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- changeset petr:check_no_flight_zone_fix_distance_trigger failOnError:true splitStatements:false

CREATE TRIGGER check_no_flight_zone_trigger
    BEFORE INSERT ON no_flight_zone
    FOR EACH ROW EXECUTE FUNCTION check_no_flight_zone();
