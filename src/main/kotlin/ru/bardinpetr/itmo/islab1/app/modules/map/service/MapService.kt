package ru.bardinpetr.itmo.meddelivery.app.modules.map.service


import io.github.dellisd.spatialk.geojson.Feature
import io.github.dellisd.spatialk.geojson.FeatureCollection
import io.github.dellisd.spatialk.geojson.dsl.feature
import org.springframework.stereotype.Service

//@Service
//class MapService(
//    private val personRepository: PersonRepository,
//    private val productRepository: ProductRepository
//) {
//
//    fun getPersons(): FeatureCollection =
//        personRepository
//            .findAll()
//            .mapNotNull(Person::toGeoFeature)
//            .let(::FeatureCollection)
//
//    fun getProducts(): FeatureCollection =
//        productRepository
//            .findAll()
//            .map(Product::toGeoFeature)
//            .let(::FeatureCollection)
//}
//
//fun Person.toGeoFeature(): Feature? {
//    return feature(
//        id = id.toString(),
//        geometry = location?.toPoint() ?: return null
//    ) {
//        put("owner", owner?.username ?: "sys")
//        put("name", name)
//        put("locName", location?.name ?: "unnamed")
//    }
//}
//
//fun Product.toGeoFeature(): Feature {
//    return feature(
//        id = id.toString(),
//        geometry = coordinates.toPoint()
//    ) {
//        put("owner", owner?.username ?: "sys")
//        put("name", name)
//        put("price", price)
//    }
//}
