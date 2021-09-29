package com.example.doctorpractice.scheduling.writemodel

import com.example.doctorpractice.scheduling.application.commands.CommandDispatcher
import com.example.doctorpractice.scheduling.application.commands.CommandHandlers
import com.example.doctorpractice.scheduling.domain.AggregateRoot
import org.assertj.core.api.Assertions
import kotlin.reflect.KClass

abstract class WriteModelTests<Aggregate : AggregateRoot<*>> {

    protected val noEvents: List<Any> = emptyList()
    protected val idGenerator: ReplayableIdGenerator = ReplayableIdGenerator()
    protected val aggregate by lazy { aggregateInstance() }

    private val commandDispatcher by lazy { CommandDispatcher(this.commandHandlers()) }
    private var error: Throwable? = null

    protected abstract fun aggregateInstance(): Aggregate
    protected abstract fun commandHandlers(): CommandHandlers

    fun given(events: List<Any> = noEvents) {
        error = null
        aggregate.load(events)
    }

    fun whenever(command: Any) {
        runCatching {
            aggregate.clearChanges()
            commandDispatcher.dispatch(command)
        }.onFailure {
            error = it
        }
        idGenerator.stopRecording()
    }

    fun then(events: List<Any>) {
        Assertions.assertThat(aggregate.changes).isEqualTo(events)
    }

    fun <T : Throwable> then(errorType: KClass<T>) {
        Assertions.assertThat(error).isNotNull()
        Assertions.assertThat(error!!::class).isEqualTo(errorType)
    }
}
