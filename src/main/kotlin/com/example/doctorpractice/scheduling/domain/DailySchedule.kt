package com.example.doctorpractice.scheduling.domain

import com.example.doctorpractice.scheduling.domain.commands.ScheduleDay
import com.example.doctorpractice.scheduling.domain.errors.SlotOverlapped
import com.example.doctorpractice.scheduling.domain.events.DayScheduled
import com.example.doctorpractice.scheduling.domain.events.SlotScheduled
import java.time.Duration
import java.time.LocalTime

class DailySchedule : AggregateRoot<DailyScheduleId>() {

    private val slots: MutableList<Slot> = mutableListOf()

    fun schedule(command: ScheduleDay, idGenerator: IdGenerator) {
        raise(DayScheduled(command.dailyScheduleId))
        command.slots.forEach {
            if (overlapsAnySlot(it.startTime, it.duration)) {
                throw SlotOverlapped()
            }
            raise(
                SlotScheduled(
                    slotId = SlotId.create(idGenerator),
                    dailyScheduleId = id!!,
                    doctorId = command.doctorId,
                    date = command.date,
                    startTime = it.startTime,
                    duration = it.duration
                )
            )
        }
    }

    override fun on(event: Any) {
        when (event) {
            is DayScheduled -> id = event.dailyScheduleId
            is SlotScheduled -> slots.add(
                Slot(
                    startTime = event.startTime,
                    duration = event.duration
                )
            )
        }
    }

    private fun overlapsAnySlot(startTime: LocalTime, duration: Duration): Boolean {
        return slots.any { it.overlapsWith(startTime, duration) }
    }
}
