package com.example.doctorpractice.scheduling.domain

import com.example.doctorpractice.scheduling.domain.commands.ScheduleDay
import com.example.doctorpractice.scheduling.domain.events.DayScheduled
import com.example.doctorpractice.scheduling.domain.events.SlotScheduled

class DailySchedule : AggregateRoot<DailyScheduleId>() {

    fun schedule(command: ScheduleDay, idGenerator: IdGenerator) {
        raise(DayScheduled(command.dailyScheduleId))
        command.slots.forEach {
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
        }
    }
}
