package com.example.doctorpractice.scheduling.domain.queries

import com.example.doctorpractice.scheduling.domain.DailyScheduleId
import com.example.doctorpractice.scheduling.domain.DoctorId
import com.example.doctorpractice.scheduling.domain.SlotId
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

data class AvailableSlot(
    val slotId: SlotId,
    val dailyScheduleId: DailyScheduleId,
    val doctorId: DoctorId,
    val date: LocalDate,
    val startTime: LocalTime,
    val duration: Duration
)
