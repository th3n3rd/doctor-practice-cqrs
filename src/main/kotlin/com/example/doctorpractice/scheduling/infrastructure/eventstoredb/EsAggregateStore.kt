package com.example.doctorpractice.scheduling.infrastructure.eventstoredb

import com.eventstore.dbclient.*
import com.example.doctorpractice.scheduling.domain.AggregateRoot
import com.example.doctorpractice.scheduling.infrastructure.common.logger
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

@Component
class EsAggregateStore(
    private val client: EventStoreDBClient,
    private val serde: EsEventSerde,
    private val context: EsStreamContext
) {
    private val logger = logger()

    fun save(aggregate: AggregateRoot<*>) {
        val eventsData = aggregate.changes.map { serde.serialise(it) }
        val appendOptions = AppendToStreamOptions
            .get()
            .expectedRevision(expectedVersion(aggregate.version))
        runCatching {
            client.appendToStream(context.withPrefix(aggregate.id!!.toString()), appendOptions, eventsData.iterator())
        }.onFailure {
            logger.error(it.message, it)
        }
    }

    fun <Aggregate : AggregateRoot<*>> load(aggregateId: String, type: KClass<Aggregate>): Aggregate {
        val readOptions = ReadStreamOptions
            .get()
            .fromStart()
            .notResolveLinkTos()

        val aggregate = type.createInstance()

        val events: List<Any> = runCatching {
            client
                .readStream(context.withPrefix(aggregateId), readOptions)
                .get()
                .events
                .map { serde.deserialise(it) }
        }.onFailure {
            if (it.cause !is StreamNotFoundException) {
                logger.error(it.message, it)
            }
        }.getOrElse { emptyList() }

        aggregate.load(events)

        return aggregate
    }

    private fun expectedVersion(version: Long) = if (version == -1L) {
        ExpectedRevision.NO_STREAM
    } else {
        ExpectedRevision.expectedRevision(version)
    }
}
