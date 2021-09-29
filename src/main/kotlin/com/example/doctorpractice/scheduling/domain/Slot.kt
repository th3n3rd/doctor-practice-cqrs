package com.example.doctorpractice.scheduling.domain

import java.time.Duration
import java.time.LocalTime

data class Slot(
    val startTime: LocalTime,
    val duration: Duration
) {
    private val endTime = startTime.plus(duration)

    fun overlapsWith(startTime: LocalTime, duration: Duration): Boolean {
        val endTime = startTime.plus(duration)
        return this.startTime <= endTime && this.endTime >= startTime
    }
}
