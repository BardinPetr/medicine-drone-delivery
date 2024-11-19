//import jakarta.persistence.*
//import java.io.Serializable
//
//@Embeddable
//data class Point(
//    val x: Double,
//    val y: Double
//)
//
//@Entity
//data class Drone(
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Int,
//
//    @ManyToOne
//    @JoinColumn(name = "model_id")
//    val typeOfDrone: TypeOfDrone,
//
//    @ManyToOne
//    @JoinColumn(name = "status_id")
//    val status: DroneStatus,
//
//    @Embedded
//    val location: Point,
//
//    @ManyToOne
//    @JoinColumn(name = "flight_task_id")
//    val flightTask: FlightTask? = null
//)
//
//@Entity
//data class TypeOfDrone(
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Int,
//    val name: String,
//    val maxWeight: Float,
//
//    @OneToMany(mappedBy = "typeOfDrone")
//    val drones: List<Drone> = listOf()
//)
//
//@Entity
//data class DroneStatus(
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Int,
//    val name: String,
//
//    @OneToMany(mappedBy = "status")
//    val drones: List<Drone> = listOf()
//)
//
//@Entity
//data class ProductType(
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Int,
//    val type: String,
//    val pieceWeight: Float
//)
//
//@Entity
//data class Warehouse(
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Int,
//    val name: String,
//
//    @Embedded
//    val location: Point,
//
//    @OneToMany(mappedBy = "warehouse")
//    val warehouseProducts: List<WarehouseProducts> = listOf()
//)
//
//@Entity
//@IdClass(WarehouseProductsId::class)
//data class WarehouseProducts(
//    @Id
//    @ManyToOne
//    @JoinColumn(name = "type_id")
//    val productType: ProductType,
//
//    @Id
//    @ManyToOne
//    @JoinColumn(name = "warehouse_id")
//    val warehouse: Warehouse,
//
//    val quantity: Int,
//    val reservedQuantity: Int
//)
//
//data class WarehouseProductsId(
//    val productType: Int = 0,
//    val warehouse: Int = 0
//) : Serializable
//
//@Entity
//data class TaskStatus(
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Int,
//    val name: String,
//
//    @OneToMany(mappedBy = "status")
//    val requests: List<Request> = listOf(),
//
//    @OneToMany(mappedBy = "status")
//    val flightTasks: List<FlightTask> = listOf()
//)
//
//@Entity
//data class Request(
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Int,
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    val user: User,
//
//    @ManyToOne
//    @JoinColumn(name = "task_status_id")
//    val status: TaskStatus,
//
//    @ManyToOne
//    @JoinColumn(name = "medical_facility_id")
//    val medicalFacility: MedicalFacility,
//
//    @OneToMany(mappedBy = "request")
//    val requestEntries: List<RequestEntry> = listOf()
//)
//
//@Entity
//data class RequestEntry(
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Int,
//
//    @ManyToOne
//    @JoinColumn(name = "request_id")
//    val request: Request,
//
//    @ManyToOne
//    @JoinColumn(name = "product_type_id")
//    val productType: ProductType,
//
//    val quantity: Int
//)
//
//@Entity
//data class FlightTask(
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Int,
//
//    @ManyToOne
//    @JoinColumn(name = "request_id")
//    val request: Request,
//
//    @ManyToOne
//    @JoinColumn(name = "task_status_id")
//    val status: TaskStatus,
//
//    @ManyToOne
//    @JoinColumn(name = "product_type_id")
//    val productType: ProductType,
//
//    @ManyToOne
//    @JoinColumn(name = "warehouse_id")
//    val warehouse: Warehouse,
//
//    @ManyToOne
//    @JoinColumn(name = "medical_facility_id")
//    val medicalFacility: MedicalFacility,
//
//    val quantity: Int,
//
//    @OneToMany(mappedBy = "flightTask")
//    val drones: List<Drone> = listOf(),
//
//    @OneToMany(mappedBy = "flightTask")
//    val routePoints: List<RoutePoint> = listOf()
//)
//
//@Entity
//@IdClass(RoutePointId::class)
//data class RoutePoint(
//    @Id
//    val pointNumber: Int,
//
//    @Id
//    @ManyToOne
//    @JoinColumn(name = "flight_task_id")
//    val flightTask: FlightTask,
//
//    @Embedded
//    val location: Point
//)
//
//data class RoutePointId(
//    val pointNumber: Int = 0,
//    val flightTask: Int = 0
//) : Serializable
//
//@Entity
//data class MedicalFacility(
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Int,
//    val name: String,
//
//    @ManyToOne
//    @JoinColumn(name = "responsible_user_id")
//    val responsibleUser: User,
//
//    @Embedded
//    val location: Point
//)
//
//@Entity
//data class User(
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Int,
//    val name: String,
//    val role: String,
//    val login: String,
//    val passwordHash: String,
//
//    @OneToMany(mappedBy = "responsibleUser")
//    val medicalFacilities: List<MedicalFacility> = listOf(),
//
//    @OneToMany(mappedBy = "user")
//    val requests: List<Request> = listOf()
//)
//
//@Entity
//data class NoFlightZone(
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Int,
//    val radius: Float,
//
//    @Embedded
//    val center: Point
//)
