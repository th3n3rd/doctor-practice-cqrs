package com.example.doctorpractice.scheduling.domain.events

import com.example.doctorpractice.scheduling.domain.DailyScheduleId

data class DayScheduled(val dailyScheduleId: DailyScheduleId)
