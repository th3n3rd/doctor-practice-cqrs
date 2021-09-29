package com.example.doctorpractice.scheduling.fixtures

import com.example.doctorpractice.scheduling.domain.DailyScheduleId
import com.example.doctorpractice.scheduling.domain.DoctorId
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

val today: LocalDate = LocalDate.now()
val eightAm: LocalTime = LocalTime.of(8, 0)
val eightPm: LocalTime = LocalTime.of(20, 0)
val tenMinutes: Duration = Duration.ofMinutes(10)
val doctorId = DoctorId(UUID.randomUUID())
val doctorTodayScheduleId = DailyScheduleId.create(doctorId, today)
