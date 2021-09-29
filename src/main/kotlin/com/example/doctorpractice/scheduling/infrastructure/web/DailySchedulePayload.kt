package com.example.doctorpractice.scheduling.infrastructure.web

import com.example.doctorpractice.scheduling.domain.DoctorId
import com.example.doctorpractice.scheduling.domain.commands.ScheduleDay
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class DailySchedulePayload(
    val doctorId: UUID,
    val date: LocalDate,
    val slots: List<Slot>
) {
    data class Slot(val startTime: LocalTime, val duration: Duration)

    fun toCommand(): ScheduleDay = ScheduleDay(
        doctorId = DoctorId(doctorId),
        date = date,
        slots = slots.map {
            ScheduleDay.Slot(
                startTime = it.startTime,
                duration = it.duration
            )
        }
    )
}
