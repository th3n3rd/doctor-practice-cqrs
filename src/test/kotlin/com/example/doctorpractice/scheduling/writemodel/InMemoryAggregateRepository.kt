package com.example.doctorpractice.scheduling.writemodel

import com.example.doctorpractice.scheduling.domain.AggregateRepository
import com.example.doctorpractice.scheduling.domain.AggregateRoot

class InMemoryAggregateRepository<Aggregate : AggregateRoot<Id>, Id>(
    private val aggregate: Aggregate
) : AggregateRepository<Aggregate, Id> {

    override fun load(id: Id): Aggregate {
        return aggregate
    }

    override fun save(aggregate: Aggregate) {}
}
