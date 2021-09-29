package com.example.doctorpractice.scheduling.domain.queries

import java.time.LocalDate

interface AvailableSlotsRepository {
    fun findAvailableSlotsOn(date: LocalDate): List<AvailableSlot>
    fun addSlot(slot: AvailableSlot)
}
