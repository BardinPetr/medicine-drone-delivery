package ru.bardinpetr.itmo.meddelivery

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class TestDataGen(
) {

    @Transactional
    fun createTestData() {
//        if (locationRepository.count() > 0) return
    }
}
