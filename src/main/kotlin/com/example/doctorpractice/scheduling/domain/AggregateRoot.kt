package com.example.doctorpractice.scheduling.domain

abstract class AggregateRoot<Id> {
    var id: Id? = null
        protected set

    var version: Long = -1
        private set

    val changes: List<Any> = mutableListOf()

    fun load(events: List<Any>) {
        events.forEach {
            raise(it)
            version += 1
        }
    }

    fun clearChanges() {
        (changes as MutableList).clear()
    }

    protected fun raise(event: Any) {
        (changes as MutableList).add(event)
        on(event)
    }

    protected abstract fun on(event: Any)
}
