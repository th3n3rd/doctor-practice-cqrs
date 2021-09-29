package com.example.doctorpractice.scheduling.infrastructure.web

import com.example.doctorpractice.scheduling.application.commands.CommandDispatcher
import com.example.doctorpractice.scheduling.application.queries.QueryDispatcher
import com.example.doctorpractice.scheduling.domain.queries.ListAvailableSlots
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
class ApiController(
    private val commandDispatcher: CommandDispatcher,
    private val queryDispatcher: QueryDispatcher
) {

    @PostMapping("/daily-schedules")
    @ResponseStatus(CREATED)
    fun scheduleDay(@RequestBody dailySchedulePayload: DailySchedulePayload) {
        commandDispatcher.dispatch(dailySchedulePayload.toCommand())
    }

    @GetMapping("/slots/{date}/available")
    fun litAvailableSlots(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate): ListAvailableSlotsPayload {
        return ListAvailableSlotsPayload.fromDomain(
            queryDispatcher.dispatch(ListAvailableSlots(date))
        )
    }
}
