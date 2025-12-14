import grpc

from mapper import zone_to_grpc, pt_to_grpc
from service_pb2 import *
from service_pb2_grpc import RouterServiceStub

channel = grpc.insecure_channel("localhost:50051")
stub = RouterServiceStub(channel)

res = stub.UpdateNoFlightZones(
    UpdateNoFlightZonesRequest(zones=[
        zone_to_grpc((59.958350, 30.331383), 3000),
        zone_to_grpc((59.918580, 30.362130), 3000),
    ])
)

res = stub.PlanRoute(
    PlanRouteRequest(src=pt_to_grpc((59.865536, 30.415766)),
                     dst=pt_to_grpc((60.023046, 30.260665)))
)

print(res)
