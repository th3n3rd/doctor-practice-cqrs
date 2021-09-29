package com.example.doctorpractice.scheduling.application.commands

class CommandDispatcher(private val commandHandlers: CommandHandlers) {
    fun dispatch(command: Any) {
        commandHandlers.handle(command)
    }
}
