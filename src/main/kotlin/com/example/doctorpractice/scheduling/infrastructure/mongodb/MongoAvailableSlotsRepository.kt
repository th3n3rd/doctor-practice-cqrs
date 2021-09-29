package com.example.doctorpractice.scheduling.infrastructure.mongodb

import com.example.doctorpractice.scheduling.domain.SlotId
import com.example.doctorpractice.scheduling.domain.queries.AvailableSlot
import com.example.doctorpractice.scheduling.domain.queries.AvailableSlotsRepository
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

interface DataRepository : MongoRepository<AvailableSlot, SlotId> {
    fun findAllByDate(date: LocalDate): List<AvailableSlot>
}

@Repository
class MongoAvailableSlotsRepository(private val dataRepository: DataRepository) : AvailableSlotsRepository {

    override fun findAvailableSlotsOn(date: LocalDate): List<AvailableSlot> {
        return dataRepository.findAllByDate(date)
    }

    override fun addSlot(slot: AvailableSlot) {
        dataRepository.save(slot)
    }
}
