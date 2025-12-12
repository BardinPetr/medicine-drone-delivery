package ru.bardinpetr.itmo.meddelivery.app.service

import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.bardinpetr.itmo.meddelivery.app.entities.Drone
import ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility
import ru.bardinpetr.itmo.meddelivery.app.entities.NoFlightZone
import ru.bardinpetr.itmo.meddelivery.app.entities.Point
import ru.bardinpetr.itmo.meddelivery.app.entities.ProductType
import ru.bardinpetr.itmo.meddelivery.app.entities.TypeOfDrone
import ru.bardinpetr.itmo.meddelivery.app.entities.Warehouse
import ru.bardinpetr.itmo.meddelivery.app.entities.WarehouseProducts
import ru.bardinpetr.itmo.meddelivery.app.entities.WarehouseProductsId
import ru.bardinpetr.itmo.meddelivery.app.entities.enums.DroneStatus
import ru.bardinpetr.itmo.meddelivery.app.repository.*
import ru.bardinpetr.itmo.meddelivery.app.service.fleet.DBDroneFleet
import ru.bardinpetr.itmo.meddelivery.app.service.map.containsPoint
import ru.bardinpetr.itmo.meddelivery.common.auth.dto.RegisterDto
import ru.bardinpetr.itmo.meddelivery.common.auth.model.UserRole
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.UserRepository
import ru.bardinpetr.itmo.meddelivery.common.auth.service.UserService
import kotlin.random.Random

@Suppress("MagicNumber")
@Service
class DemoDataGenerator(
    val em: EntityManager,
    val userRepo: UserRepository,
    val userService: UserService,
    val mfRepo: MedicalFacilityRepository,
    val wRepo: WarehouseRepository,
    val dTypeRepo: TypeOfDroneRepository,
    val droneRepo: DroneRepository,
    val nfzRepo: NoFlightZoneRepository,
    val pTypeRepo: ProductTypeRepository,
    val whProducts: WarehouseProductsRepository,
    val fleet: DBDroneFleet
) {
    @Autowired
    @Lazy
    private lateinit var _self: DemoDataGenerator
    private val self
        get() = if (this::_self.isInitialized) _self else this

    var noFlightZones: List<NoFlightZone> = emptyList()

    fun launch() {
        self.cleanup()
        self.makeUsers()
        noFlightZones = self.makeNoFlightZones()
        self.makeWarehouses()
        self.makeMedicalFacilities()
        self.makeDrones()
        self.makeProducts()
        fleet.makeFleet()
    }

    private fun randomPoint(): Point {
        val p = Point(
            Random.nextDouble(59.8, 60.0),
            Random.nextDouble(30.1, 30.7)
        )
        return if (noFlightZones.any { it.containsPoint(p) }) randomPoint() else p
    }

    @Transactional
    fun cleanup() =
        """
            TRUNCATE TABLE drone CASCADE;
            TRUNCATE TABLE flight_task CASCADE;
            TRUNCATE TABLE medical_facility CASCADE;
            TRUNCATE TABLE no_flight_zone CASCADE;
            TRUNCATE TABLE product_type CASCADE;
            TRUNCATE TABLE request CASCADE;
            TRUNCATE TABLE request_entry CASCADE;
            TRUNCATE TABLE rev_info CASCADE;
            TRUNCATE TABLE route_point CASCADE;
            TRUNCATE TABLE type_of_drone CASCADE;
            TRUNCATE TABLE "user" CASCADE;
            TRUNCATE TABLE user_audit CASCADE;
            TRUNCATE TABLE warehouse CASCADE;
            TRUNCATE TABLE warehouse_products CASCADE;
        """
            .let(em::createNativeQuery)
            .executeUpdate()

    @Transactional
    fun makeUsers() {
        userService.register(RegisterDto("admin", "12345678", UserRole.ADMIN))
        userService.register(RegisterDto("w1", "12345678", UserRole.WAREHOUSE))
        userService.register(RegisterDto("w2", "12345678", UserRole.WAREHOUSE))
        userService.register(RegisterDto("m1", "12345678", UserRole.MEDIC))
        userService.register(RegisterDto("m2", "12345678", UserRole.MEDIC))
    }

    @Transactional
    fun makeMedicalFacilities() {
        val medics = userRepo.findAllByRoleIs(UserRole.MEDIC)
        mfRepo.saveAllAndFlush(
            medics.mapIndexed { i, mUser ->
                MedicalFacility(
                    name = "MF#$i",
                    responsibleUser = mUser,
                    location = randomPoint()
                )
            }
        )
    }

    @Transactional
    fun makeWarehouses() {
        wRepo.saveAllAndFlush(
            (1..5).map {
                Warehouse(
                    name = "WH#$it",
                    location = randomPoint(),
                    products = mutableListOf()
                )
            }
        )
    }

    @Transactional
    fun makeDrones() {
        val types = dTypeRepo.saveAllAndFlush(
            listOf(
                TypeOfDrone("D-1", 5L, 1200.0),
                TypeOfDrone("D-2", 10L, 900.0),
            )
        )
        droneRepo.saveAllAndFlush(
            (0..9).map {
                Drone(
                    typeOfDrone = types.random(),
                    status = DroneStatus.IDLE,
                    location = randomPoint(),
                )
            }
        )
    }

    @Transactional
    fun makeNoFlightZones(): List<NoFlightZone> = nfzRepo.saveAllAndFlush(
        (0..10).map {
            NoFlightZone(
                radius = Random.nextDouble(1000.0, 2000.0).toFloat(),
                center = randomPoint(),
            )
        }
    )

    @Transactional
    fun makeProducts() {
        val types = pTypeRepo.saveAllAndFlush(
            listOf(
                ProductType("P1"),
                ProductType("P2"),
                ProductType("P3"),
            )
        )

        wRepo.findAll()
            .flatMap { wh ->
                types.map { ptyp ->
                    WarehouseProducts(
                        id = WarehouseProductsId(ptyp.id!!, wh.id!!),
                        product = ptyp,
                        warehouse = wh,
                        quantity = Random.nextInt(0, 20)
                    )
                }
            }
            .let(whProducts::saveAllAndFlush)
    }
}
