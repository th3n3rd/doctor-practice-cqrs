package com.example.doctorpractice.scheduling.domain.queries

import com.example.doctorpractice.scheduling.domain.DoctorId

data class AvailableSlotsByDoctor(val slots: Map<DoctorId, List<AvailableSlot>>) {
    companion object {
        fun create(slots: List<AvailableSlot>): AvailableSlotsByDoctor {
            return AvailableSlotsByDoctor(
                slots
                    .sortedBy { it.startTime }
                    .groupBy { it.doctorId }
            )
        }
    }
}
