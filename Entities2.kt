Here are the updated entity definitions with `@Column(nullable = false)` or `@Column(nullable = true)` annotations, based on the specified cardinalities.

```kotlin
import jakarta.persistence.*
import java.io.Serializable

@Embeddable
data class Point(
    val x: Double,
    val y: Double
)

@Entity
data class Drone(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    val typeOfDrone: TypeOfDrone,

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    val status: DroneStatus,

    @Embedded
    val location: Point,

    @ManyToOne
    @JoinColumn(name = "flight_task_id")
    @Column(nullable = true)
    val flightTask: FlightTask? = null
)

@Entity
data class TypeOfDrone(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val maxWeight: Float,

    @OneToMany(mappedBy = "typeOfDrone")
    val drones: List<Drone> = listOf()
)

@Entity
data class DroneStatus(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    val name: String,

    @OneToMany(mappedBy = "status")
    val drones: List<Drone> = listOf()
)

@Entity
data class ProductType(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    val type: String,

    @Column(nullable = false)
    val pieceWeight: Float
)

@Entity
data class Warehouse(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    val name: String,

    @Embedded
    val location: Point,

    @OneToMany(mappedBy = "warehouse")
    val warehouseProducts: List<WarehouseProducts> = listOf()
)

@Entity
@IdClass(WarehouseProductsId::class)
data class WarehouseProducts(
    @Id
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    val productType: ProductType,

    @Id
    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    val warehouse: Warehouse,

    @Column(nullable = false)
    val quantity: Int,

    @Column(nullable = false)
    val reservedQuantity: Int
)

data class WarehouseProductsId(
    val productType: Int = 0,
    val warehouse: Int = 0
) : Serializable

@Entity
data class TaskStatus(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    val name: String,

    @OneToMany(mappedBy = "status")
    val requests: List<Request> = listOf(),

    @OneToMany(mappedBy = "status")
    val flightTasks: List<FlightTask> = listOf()
)

@Entity
data class Request(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne
    @JoinColumn(name = "task_status_id", nullable = false)
    val status: TaskStatus,

    @ManyToOne
    @JoinColumn(name = "medical_facility_id", nullable = false)
    val medicalFacility: MedicalFacility,

    @OneToMany(mappedBy = "request")
    val requestEntries: List<RequestEntry> = listOf()
)

@Entity
data class RequestEntry(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    val request: Request,

    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = true)
    val productType: ProductType? = null,

    @Column(nullable = false)
    val quantity: Int
)

@Entity
data class FlightTask(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    val request: Request,

    @ManyToOne
    @JoinColumn(name = "task_status_id", nullable = false)
    val status: TaskStatus,

    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = false)
    val productType: ProductType,

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    @Column(nullable = true)
    val warehouse: Warehouse? = null,

    @ManyToOne
    @JoinColumn(name = "medical_facility_id")
    @Column(nullable = true)
    val medicalFacility: MedicalFacility? = null,

    @Column(nullable = false)
    val quantity: Int,

    @OneToMany(mappedBy = "flightTask")
    val drones: List<Drone> = listOf(),

    @OneToMany(mappedBy = "flightTask")
    val routePoints: List<RoutePoint> = listOf()
)

@Entity
@IdClass(RoutePointId::class)
data class RoutePoint(
    @Id
    val pointNumber: Int,

    @Id
    @ManyToOne
    @JoinColumn(name = "flight_task_id", nullable = false)
    val flightTask: FlightTask,

    @Embedded
    val location: Point
)

data class RoutePointId(
    val pointNumber: Int = 0,
    val flightTask: Int = 0
) : Serializable

@Entity
data class MedicalFacility(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    val name: String,

    @ManyToOne
    @JoinColumn(name = "responsible_user_id", nullable = true)
    val responsibleUser: User? = null,

    @Embedded
    val location: Point
)

@Entity
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val role: String,

    @Column(nullable = false)
    val login: String,

    @Column(nullable = false)
    val passwordHash: String,

    @OneToMany(mappedBy = "responsibleUser")
    val medicalFacilities: List<MedicalFacility> = listOf(),

    @OneToMany(mappedBy = "user")
    val requests: List<Request> = listOf()
)

@Entity
data class NoFlightZone(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    val radius: Float,

    @Embedded
    val center: Point
)
```

This configuration includes the `nullable` attribute on each field based on your cardinality specifications. Let me know if you need further modifications!
