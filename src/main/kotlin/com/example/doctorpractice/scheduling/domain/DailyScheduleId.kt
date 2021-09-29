package com.example.doctorpractice.scheduling.domain

import java.time.LocalDate

data class DailyScheduleId(val value: String) {
    companion object {
        fun create(doctorId: DoctorId, date: LocalDate): DailyScheduleId {
            return DailyScheduleId("${doctorId.value}-$date")
        }
    }
}
