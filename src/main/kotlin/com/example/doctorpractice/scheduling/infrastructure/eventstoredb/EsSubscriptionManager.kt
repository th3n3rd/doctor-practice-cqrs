package com.example.doctorpractice.scheduling.infrastructure.eventstoredb

import com.eventstore.dbclient.*
import com.example.doctorpractice.scheduling.application.projections.Projection
import com.example.doctorpractice.scheduling.infrastructure.common.logger
import org.springframework.stereotype.Component

@Component
class EsSubscriptionManager(
    private val client: EventStoreDBClient,
    private val serde: EsEventSerde,
    private val context: EsStreamContext,
    private val projections: List<Projection>,
) {
    private val logger = logger()

    fun start() {
        val filter = SubscriptionFilterBuilder()
            .withStreamNamePrefix(context.withPrefix())
            .build()

        val options = SubscribeToAllOptions
            .get()
            .filter(filter)
            .fromStart()
            .notResolveLinkTos()

        runCatching {
            client.subscribeToAll(Listener(projections), options)
        }.onFailure {
            logger.error(it.message, it)
        }
    }

    inner class Listener(private val projections: List<Projection>) : SubscriptionListener() {
        override fun onEvent(subscription: Subscription, serialisedEvent: ResolvedEvent) {
            runCatching {
                val event = serde.deserialise(serialisedEvent)
                projections.forEach { it.project(event) }
            }.onFailure {
                logger.error(it.message, it)
            }
        }
    }
}
