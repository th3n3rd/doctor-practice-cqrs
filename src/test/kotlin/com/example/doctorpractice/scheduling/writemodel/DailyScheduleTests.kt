package com.example.doctorpractice.scheduling.writemodel

import com.example.doctorpractice.scheduling.application.commands.CommandHandlers
import com.example.doctorpractice.scheduling.domain.DailySchedule
import com.example.doctorpractice.scheduling.domain.DailyScheduleId
import com.example.doctorpractice.scheduling.domain.SlotId
import com.example.doctorpractice.scheduling.domain.commands.ScheduleDay
import com.example.doctorpractice.scheduling.domain.errors.SlotOverlapped
import com.example.doctorpractice.scheduling.domain.events.DayScheduled
import com.example.doctorpractice.scheduling.domain.events.SlotScheduled
import com.example.doctorpractice.scheduling.fixtures.*
import org.junit.jupiter.api.Test

class DailyScheduleTests : WriteModelTests<DailySchedule>() {

    private val repository = InMemoryAggregateRepository(aggregate)

    override fun aggregateInstance() = DailySchedule()
    override fun commandHandlers() = CommandHandlers(repository, idGenerator)

    @Test
    fun `slots scheduled by a single doctor for a day`() {
        given(noEvents)
        whenever(
            ScheduleDay(
                doctorId,
                today,
                listOf(
                    ScheduleDay.Slot(eightAm, tenMinutes),
                    ScheduleDay.Slot(eightPm, tenMinutes),
                )
            )
        )
        then(
            listOf(
                DayScheduled(DailyScheduleId.create(doctorId, today)),
                SlotScheduled(
                    SlotId.create(idGenerator),
                    doctorTodayScheduleId,
                    doctorId,
                    today,
                    eightAm,
                    tenMinutes
                ),
                SlotScheduled(
                    SlotId.create(idGenerator),
                    doctorTodayScheduleId,
                    doctorId,
                    today,
                    eightPm,
                    tenMinutes
                ),
            )
        )
    }

    @Test
    fun `slots scheduled by a single doctor cannot overlap`() {
        given(noEvents)
        whenever(
            ScheduleDay(
                doctorId,
                today,
                listOf(
                    ScheduleDay.Slot(eightAm, tenMinutes),
                    ScheduleDay.Slot(eightAm.plusMinutes(1), tenMinutes),
                )
            )
        )
        then(SlotOverlapped::class)
    }
}
