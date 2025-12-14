from google.protobuf import empty_pb2 as _empty_pb2
from google.protobuf.internal import containers as _containers
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from collections.abc import Iterable as _Iterable, Mapping as _Mapping
from typing import ClassVar as _ClassVar, Optional as _Optional, Union as _Union

DESCRIPTOR: _descriptor.FileDescriptor

class Point(_message.Message):
    __slots__ = ("lat", "lon")
    LAT_FIELD_NUMBER: _ClassVar[int]
    LON_FIELD_NUMBER: _ClassVar[int]
    lat: float
    lon: float
    def __init__(self, lat: _Optional[float] = ..., lon: _Optional[float] = ...) -> None: ...

class NoFlightZone(_message.Message):
    __slots__ = ("center", "radiusMeters")
    CENTER_FIELD_NUMBER: _ClassVar[int]
    RADIUSMETERS_FIELD_NUMBER: _ClassVar[int]
    center: Point
    radiusMeters: float
    def __init__(self, center: _Optional[_Union[Point, _Mapping]] = ..., radiusMeters: _Optional[float] = ...) -> None: ...

class UpdateNoFlightZonesRequest(_message.Message):
    __slots__ = ("zones",)
    ZONES_FIELD_NUMBER: _ClassVar[int]
    zones: _containers.RepeatedCompositeFieldContainer[NoFlightZone]
    def __init__(self, zones: _Optional[_Iterable[_Union[NoFlightZone, _Mapping]]] = ...) -> None: ...

class PlanRouteRequest(_message.Message):
    __slots__ = ("src", "dst")
    SRC_FIELD_NUMBER: _ClassVar[int]
    DST_FIELD_NUMBER: _ClassVar[int]
    src: Point
    dst: Point
    def __init__(self, src: _Optional[_Union[Point, _Mapping]] = ..., dst: _Optional[_Union[Point, _Mapping]] = ...) -> None: ...

class PlanRouteResponse(_message.Message):
    __slots__ = ("route",)
    ROUTE_FIELD_NUMBER: _ClassVar[int]
    route: _containers.RepeatedCompositeFieldContainer[Point]
    def __init__(self, route: _Optional[_Iterable[_Union[Point, _Mapping]]] = ...) -> None: ...
