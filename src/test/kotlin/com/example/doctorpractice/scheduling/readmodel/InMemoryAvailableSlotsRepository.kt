package com.example.doctorpractice.scheduling.readmodel

import com.example.doctorpractice.scheduling.domain.queries.AvailableSlot
import com.example.doctorpractice.scheduling.domain.queries.AvailableSlotsRepository
import java.time.LocalDate

class InMemoryAvailableSlotsRepository : AvailableSlotsRepository {
    private val slots: MutableList<AvailableSlot> = mutableListOf()

    override fun findAvailableSlotsOn(date: LocalDate): List<AvailableSlot> {
        return slots.filter { it.date == date }
    }

    override fun addSlot(slot: AvailableSlot) {
        slots.add(slot)
    }
}
