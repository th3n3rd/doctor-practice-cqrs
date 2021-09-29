package com.example.doctorpractice.scheduling.application.commands

import com.example.doctorpractice.scheduling.domain.DailySchedule
import com.example.doctorpractice.scheduling.domain.DailyScheduleId
import com.example.doctorpractice.scheduling.domain.DailyScheduleRepository
import com.example.doctorpractice.scheduling.domain.IdGenerator
import com.example.doctorpractice.scheduling.domain.commands.ScheduleDay

class CommandHandlers(
    private val repository: DailyScheduleRepository,
    private val idGenerator: IdGenerator
) {

    fun handle(command: Any) {
        when (command) {
            is ScheduleDay -> transactional(command.dailyScheduleId) { it.schedule(command, idGenerator) }
            else -> throw IllegalArgumentException("Cannot handle command of type ${command::javaClass}")
        }
    }

    private fun transactional(id: DailyScheduleId, handler: (aggregate: DailySchedule) -> Unit) {
        val aggregate = repository.load(id)
        handler.invoke(aggregate)
        repository.save(aggregate)
    }
}
