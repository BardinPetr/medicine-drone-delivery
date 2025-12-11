import json
import math
from extremitypathfinder import PolygonEnvironment

def degrees_per_meter(lat):
    a = 6378137.0
    b = 6356752.314245
    e2 = 1 - (b**2 / a**2)
    lat_rad = math.radians(lat)
    meridional = a * (1 - e2) / (1 - e2 * math.sin(lat_rad)**2)**(3/2)
    parallel = a * math.cos(lat_rad) / math.sqrt(1 - e2 * math.sin(lat_rad)**2)
    return math.degrees(1 / meridional), math.degrees(1 / parallel)

def get_bbox(circle):
    lat, lon, radius = circle
    dlat, dlon = degrees_per_meter(lat)
    r_lat, r_lon = radius * dlat, radius * dlon
    top_left = (lat - r_lat, lon + r_lon)
    top_right = (lat + r_lat, lon + r_lon)
    bottom_right = (lat + r_lat, lon - r_lon)
    bottom_left = (lat - r_lat, lon - r_lon)
    return [top_left, top_right, bottom_right, bottom_left]


def find_path(p1, p2, holes):
    bound = [(-180, -90), (180, -90), (180, 90), (-180, 90)]
    env = PolygonEnvironment()
    env.store(bound, holes, validate=False)
    try:
        return env.find_shortest_path(p1, p2)[0]
    except ValueError:
        return [p1, p2]


def run(data):
    holes = [get_bbox(c) for c in data['circles']]
    out = find_path(data['p1'], data['p2'], holes)
    return json.dumps(out)


data = json.loads(input())
print(run(data))
