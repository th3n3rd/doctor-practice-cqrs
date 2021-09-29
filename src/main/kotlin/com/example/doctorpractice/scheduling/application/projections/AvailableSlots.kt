package com.example.doctorpractice.scheduling.application.projections

import com.example.doctorpractice.scheduling.domain.events.SlotScheduled
import com.example.doctorpractice.scheduling.domain.queries.AvailableSlot
import com.example.doctorpractice.scheduling.domain.queries.AvailableSlotsRepository

class AvailableSlots(private val repository: AvailableSlotsRepository) : Projection {

    override fun project(event: Any) {
        when (event) {
            is SlotScheduled -> repository.addSlot(availableSlot(event))
        }
    }

    private fun availableSlot(event: SlotScheduled): AvailableSlot {
        return AvailableSlot(
            event.slotId,
            event.dailyScheduleId,
            event.doctorId,
            event.date,
            event.startTime,
            event.duration
        )
    }
}
