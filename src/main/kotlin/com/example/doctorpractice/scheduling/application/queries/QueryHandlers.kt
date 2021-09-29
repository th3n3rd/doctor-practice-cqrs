package com.example.doctorpractice.scheduling.application.queries

import com.example.doctorpractice.scheduling.domain.queries.AvailableSlotsByDoctor
import com.example.doctorpractice.scheduling.domain.queries.AvailableSlotsRepository
import com.example.doctorpractice.scheduling.domain.queries.ListAvailableSlots

class QueryHandlers(private val repository: AvailableSlotsRepository) {
    inline fun <reified T : Any> handle(query: Any): T {
        return when (query) {
            is ListAvailableSlots -> listAvailableSlots(query) as T
            else -> throw IllegalArgumentException("Cannot handle query of type ${query::javaClass}")
        }
    }

    fun listAvailableSlots(query: ListAvailableSlots): AvailableSlotsByDoctor {
        return AvailableSlotsByDoctor.create(repository.findAvailableSlotsOn(query.date))
    }
}
