from typing import List

from models import PtModel, ZoneModel
from service_pb2 import Point, NoFlightZone, PlanRouteResponse


def pt_to_grpc(pt: PtModel) -> Point:
    return Point(lat=pt[0], lon=pt[1])


def pt_from_grpc(pt: Point) -> PtModel:
    return pt.lat, pt.lon


def pts_to_grpc(pts: List[PtModel]) -> PlanRouteResponse:
    return PlanRouteResponse(route=[pt_to_grpc(i) for i in pts])


def zone_from_grpc(zone: NoFlightZone) -> ZoneModel:
    return ZoneModel(
        center=pt_from_grpc(zone.center),
        radius=zone.radiusMeters
    )


def zone_to_grpc(center: PtModel, radius: float) -> NoFlightZone:
    return NoFlightZone(
        center=pt_to_grpc(center), radiusMeters=radius
    )
