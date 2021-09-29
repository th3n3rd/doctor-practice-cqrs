package com.example.doctorpractice.scheduling.domain.commands

import com.example.doctorpractice.scheduling.domain.DailyScheduleId
import com.example.doctorpractice.scheduling.domain.DoctorId
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

data class ScheduleDay(
    val doctorId: DoctorId,
    val date: LocalDate,
    val slots: List<Slot>
) {
    val dailyScheduleId = DailyScheduleId.create(doctorId, date)

    data class Slot(
        val startTime: LocalTime,
        val duration: Duration
    )
}
