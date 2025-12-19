import math
from typing import Tuple

from models import ZoneModel, PtModel


def degrees_per_meter(lat: float) -> Tuple[float, float]:
    a = 6378137.0
    b = 6356752.314245
    e2 = 1 - (b ** 2 / a ** 2)
    lat_rad = math.radians(lat)
    meridional = a * (1 - e2) / (1 - e2 * math.sin(lat_rad) ** 2) ** (3 / 2)
    parallel = a * math.cos(lat_rad) / math.sqrt(1 - e2 * math.sin(lat_rad) ** 2)
    return math.degrees(1 / meridional), math.degrees(1 / parallel)


def get_bbox(zone: ZoneModel) -> Tuple[PtModel, PtModel, PtModel, PtModel]:
    lat, lon, radius = (*zone.center, zone.radius)
    dlat, dlon = degrees_per_meter(lat)
    r_lat, r_lon = radius * dlat, radius * dlon
    top_left = (lat - r_lat, lon + r_lon)
    top_right = (lat + r_lat, lon + r_lon)
    bottom_right = (lat + r_lat, lon - r_lon)
    bottom_left = (lat - r_lat, lon - r_lon)
    return [top_left, top_right, bottom_right, bottom_left]
