import os
from concurrent.futures import ThreadPoolExecutor
from typing import List

import grpc
from extremitypathfinder import PolygonEnvironment
from google.protobuf import empty_pb2

from geo import get_bbox
from mapper import zone_from_grpc, pt_from_grpc, pts_to_grpc
from models import PtModel
from service_pb2 import PlanRouteRequest, UpdateNoFlightZonesRequest, PlanRouteResponse
from service_pb2_grpc import RouterServiceServicer, add_RouterServiceServicer_to_server


class RouterServiceService(RouterServiceServicer):
    def __init__(self):
        self.geo_bound = [(-180, -90), (180, -90), (180, 90), (-180, 90)]
        self.__update_polygon_env()

    def __update_polygon_env(self, holes: List[List[PtModel]] = None):
        self.polygon_env = PolygonEnvironment()
        self.polygon_env.store(self.geo_bound, holes or [], validate=False)

    def UpdateNoFlightZones(self, request: UpdateNoFlightZonesRequest, context):
        zones = [zone_from_grpc(i) for i in request.zones]
        holes = [get_bbox(i) for i in zones]
        self.__update_polygon_env(holes)
        return empty_pb2.Empty()

    def PlanRoute(self, request: PlanRouteRequest, context) -> PlanRouteResponse:
        points = [pt_from_grpc(i) for i in (request.src, request.dst)]
        try:
            route = self.polygon_env.find_shortest_path(*points)[0]
        except ValueError:
            route = points
        return pts_to_grpc(route)


def main():
    server = grpc.server(ThreadPoolExecutor(max_workers=os.cpu_count()))
    service = RouterServiceService()
    add_RouterServiceServicer_to_server(service, server)
    server.add_insecure_port("0.0.0.0:50051")
    server.start()
    server.wait_for_termination()


if __name__ == '__main__':
    main()
