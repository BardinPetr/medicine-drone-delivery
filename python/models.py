from dataclasses import dataclass
from typing import Tuple

PtModel = Tuple[float, float]


@dataclass
class ZoneModel:
    center: PtModel
    radius: float
