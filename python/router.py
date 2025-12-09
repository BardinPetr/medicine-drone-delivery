import json

from extremitypathfinder import PolygonEnvironment


def get_bbox(circle):
    x, y, radius = circle
    top_left = (x - radius, y + radius)
    top_right = (x + radius, y + radius)
    bottom_right = (x + radius, y - radius)
    bottom_left = (x - radius, y - radius)
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
