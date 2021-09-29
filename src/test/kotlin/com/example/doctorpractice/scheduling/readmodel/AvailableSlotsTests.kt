package com.example.doctorpractice.scheduling.readmodel

import com.example.doctorpractice.scheduling.application.projections.AvailableSlots
import com.example.doctorpractice.scheduling.application.queries.QueryHandlers
import com.example.doctorpractice.scheduling.domain.SlotId
import com.example.doctorpractice.scheduling.domain.events.SlotScheduled
import com.example.doctorpractice.scheduling.domain.queries.AvailableSlot
import com.example.doctorpractice.scheduling.domain.queries.AvailableSlotsByDoctor
import com.example.doctorpractice.scheduling.domain.queries.ListAvailableSlots
import com.example.doctorpractice.scheduling.fixtures.*
import org.junit.jupiter.api.Test

class AvailableSlotsTests : ReadModelTests() {

    private val repository = InMemoryAvailableSlotsRepository()

    override fun projectionInstance() = AvailableSlots(repository)
    override fun queryHandlers() = QueryHandlers(repository)

    @Test
    fun `scheduled slots become available`() {
        val slotId = SlotId.create(idGenerator)
        given(
            listOf(
                SlotScheduled(
                    slotId,
                    doctorTodayScheduleId,
                    doctorId,
                    today,
                    eightAm,
                    tenMinutes
                )
            )
        )
        whenever(ListAvailableSlots(today))
        then(
            AvailableSlotsByDoctor(
                mapOf(
                    doctorId to listOf(
                        AvailableSlot(
                            slotId,
                            doctorTodayScheduleId,
                            doctorId,
                            today,
                            eightAm,
                            tenMinutes
                        )
                    )
                )
            )
        )
    }
}
