package com.example.doctorpractice.scheduling.domain

interface AggregateRepository<Aggregate : AggregateRoot<Id>, Id> {
    fun load(id: Id): Aggregate
    fun save(aggregate: Aggregate)
}
