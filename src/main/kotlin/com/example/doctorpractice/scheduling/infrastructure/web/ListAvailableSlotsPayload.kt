package com.example.doctorpractice.scheduling.infrastructure.web

import com.example.doctorpractice.scheduling.domain.queries.AvailableSlotsByDoctor
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class ListAvailableSlotsPayload(val slots: Map<UUID, List<Slot>>) {

    data class Slot(
        val slotId: UUID,
        val dailyScheduleId: String,
        val doctorId: UUID,
        val date: LocalDate,
        val startTime: LocalTime,
        val duration: Duration
    )

    companion object {
        fun fromDomain(availableSlotsByDoctor: AvailableSlotsByDoctor): ListAvailableSlotsPayload {
            return ListAvailableSlotsPayload(
                availableSlotsByDoctor.slots
                    .mapKeys { it.key.value }
                    .mapValues { slots ->
                        slots.value.map {
                            Slot(
                                slotId = it.slotId.value,
                                dailyScheduleId = it.dailyScheduleId.value,
                                doctorId = slots.key,
                                date = it.date,
                                startTime = it.startTime,
                                duration = it.duration
                            )
                        }
                    }
            )
        }
    }
}
