
    import {Injectable, Injector, Type} from '@angular/core';
    import {TypeOfDroneControllerService} from "../../lib"
import {NoFlightZoneControllerService} from "../../lib"
import {RequestControllerService} from "../../lib"
import {DroneControllerService} from "../../lib"
import {MedicalFacilityControllerService} from "../../lib"
import {RequestEntryControllerService} from "../../lib"
import {RoutePointControllerService} from "../../lib"
import {WarehouseControllerService} from "../../lib"
import {FlightTaskControllerService} from "../../lib"
import {PointControllerService} from "../../lib"
import {RouteControllerService} from "../../lib"
import {WarehouseProductsControllerService} from "../../lib"
import {TaskStatusControllerService} from "../../lib"
import {ProductTypeControllerService} from "../../lib"

    @Injectable({
      providedIn: 'root'
    })
    export class ApiProviderService {

      private services: { [key: string]: Type<any> } = {
        TypeOfDrone: TypeOfDroneControllerService,
NoFlightZone: NoFlightZoneControllerService,
Request: RequestControllerService,
Drone: DroneControllerService,
MedicalFacility: MedicalFacilityControllerService,
RequestEntry: RequestEntryControllerService,
RoutePoint: RoutePointControllerService,
Warehouse: WarehouseControllerService,
FlightTask: FlightTaskControllerService,
Point: PointControllerService,
Route: RouteControllerService,
WarehouseProducts: WarehouseProductsControllerService,
TaskStatus: TaskStatusControllerService,
ProductType: ProductTypeControllerService,
      }

      constructor(
        private injector: Injector
      ) {
      }

      getAPI(id: string): any {
        return this.injector.get(this.services[id])
      }
    }
