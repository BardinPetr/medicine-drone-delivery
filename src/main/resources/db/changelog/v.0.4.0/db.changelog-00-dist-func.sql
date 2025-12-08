-- liquibase formatted sql


-- changeset petr:distance_function failOnError:true splitStatements:false

CREATE OR REPLACE FUNCTION gps_distance(
    lat1 DOUBLE PRECISION,
    lon1 DOUBLE PRECISION,
    lat2 DOUBLE PRECISION,
    lon2 DOUBLE PRECISION
) RETURNS DOUBLE PRECISION AS
$$
DECLARE
    dlat DOUBLE PRECISION;
    dlon DOUBLE PRECISION;
    tmp  DOUBLE PRECISION;
BEGIN
    dlat := RADIANS(lat2 - lat1);
    dlon := RADIANS(lon2 - lon1);
    lat1 := RADIANS(lat1);
    lat2 := RADIANS(lat2);
    tmp := SIN(dlat / 2) * SIN(dlat / 2) +
           COS(lat1) * COS(lat2) * SIN(dlon / 2) * SIN(dlon / 2);
    RETURN 6371000.0 * 2 * ATAN2(SQRT(tmp), SQRT(1 - tmp));
END;
$$ LANGUAGE plpgsql;
