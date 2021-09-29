package com.example.doctorpractice.scheduling.readmodel

import com.example.doctorpractice.scheduling.application.projections.Projection
import com.example.doctorpractice.scheduling.application.queries.QueryDispatcher
import com.example.doctorpractice.scheduling.application.queries.QueryHandlers
import com.example.doctorpractice.scheduling.domain.RandomIdGenerator
import org.assertj.core.api.Assertions.assertThat

abstract class ReadModelTests {

    protected val idGenerator = RandomIdGenerator()

    private val queryDispatcher by lazy { QueryDispatcher(queryHandlers()) }
    private var readModel: Any? = null

    protected abstract fun projectionInstance(): Projection
    protected abstract fun queryHandlers(): QueryHandlers

    fun given(events: List<Any>) {
        events.forEach { projectionInstance().project(it) }
    }

    fun whenever(query: Any) {
        readModel = queryDispatcher.dispatch(query)
    }

    fun then(expectedState: Any) {
        assertThat(readModel).isEqualTo(expectedState)
    }
}
