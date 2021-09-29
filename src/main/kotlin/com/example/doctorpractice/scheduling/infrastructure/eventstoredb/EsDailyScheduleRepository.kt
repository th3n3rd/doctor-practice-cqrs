package com.example.doctorpractice.scheduling.infrastructure.eventstoredb

import com.example.doctorpractice.scheduling.domain.DailySchedule
import com.example.doctorpractice.scheduling.domain.DailyScheduleId
import com.example.doctorpractice.scheduling.domain.DailyScheduleRepository
import org.springframework.stereotype.Repository

@Repository
class EsDailyScheduleRepository(private val aggregateStore: EsAggregateStore) : DailyScheduleRepository {

    override fun load(id: DailyScheduleId): DailySchedule {
        return aggregateStore.load(id.value, DailySchedule::class)
    }

    override fun save(aggregate: DailySchedule) {
        aggregateStore.save(aggregate)
    }
}
