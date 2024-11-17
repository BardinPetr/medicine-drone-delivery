-- liquibase formatted sql

-- changeset artem:check_no_flight_zone failOnError:true splitStatements:false
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
            distance := sqrt(
                    power(NEW.lat - facility.lat, 2) +
                    power(NEW.lon - facility.lon, 2)
                        );
            IF distance < NEW.radius THEN
                RAISE EXCEPTION 'No flight zone includes a medical facility';
            END IF;
        END LOOP;

    -- Check each warehouse
    FOR warehouse IN SELECT * FROM warehouse LOOP
            distance := sqrt(
                    power(NEW.lat - warehouse.lat, 2) +
                    power(NEW.lon - warehouse.lon, 2)
                        );
            IF distance < NEW.radius THEN
                RAISE EXCEPTION 'No flight zone includes a warehouse';
            END IF;
        END LOOP;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_no_flight_zone_trigger
    BEFORE INSERT ON no_flight_zone
    FOR EACH ROW EXECUTE FUNCTION check_no_flight_zone();


-- changeset artem:check_location_in_zone failOnError:true splitStatements:false

DROP TRIGGER IF EXISTS check_medical_facility_zone_trigger ON medical_facility;
DROP TRIGGER IF EXISTS check_warehouse_zone_trigger ON warehouse;
DROP FUNCTION IF EXISTS check_location_in_zone;

CREATE OR REPLACE FUNCTION check_location_in_zone()
    RETURNS TRIGGER AS $$
DECLARE
    zone RECORD;
    distance DOUBLE PRECISION;
BEGIN
    FOR zone IN SELECT * FROM no_flight_zone LOOP
            distance := sqrt(
                    power(NEW.lat - zone.lat, 2) +
                    power(NEW.lon - zone.lon, 2)
                        );
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

-- Trigger for warehouse
CREATE TRIGGER check_warehouse_zone_trigger
    BEFORE INSERT ON warehouse
    FOR EACH ROW EXECUTE FUNCTION check_location_in_zone();

-- Trigger for medical_facility
CREATE TRIGGER check_medical_facility_zone_trigger
    BEFORE INSERT ON medical_facility
    FOR EACH ROW EXECUTE FUNCTION check_location_in_zone();
