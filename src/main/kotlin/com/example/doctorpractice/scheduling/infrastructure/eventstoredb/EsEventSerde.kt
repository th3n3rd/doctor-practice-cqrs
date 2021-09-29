package com.example.doctorpractice.scheduling.infrastructure.eventstoredb

import com.eventstore.dbclient.EventData
import com.eventstore.dbclient.ResolvedEvent
import com.example.doctorpractice.scheduling.domain.events.DayScheduled
import com.example.doctorpractice.scheduling.domain.events.SlotScheduled
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import java.util.*

@Component
class EsEventSerde(private val mapper: ObjectMapper) {
    private val contentType = "application/json"

    fun serialise(event: Any): EventData {
        return EventData(
            eventId(),
            eventType(event),
            contentType,
            eventBytes(event),
            null
        )
    }

    fun deserialise(serialisedEvent: ResolvedEvent): Any {
        val event = serialisedEvent.event
        return when (event.eventType) {
            "day-scheduled" -> originalEvent(event.eventData, DayScheduled::class.java)
            "slot-scheduled" -> originalEvent(event.eventData, SlotScheduled::class.java)
            else -> throw IllegalStateException("Cannot deserialise event of type ${event.eventType}")
        }
    }

    private fun <T> originalEvent(data: ByteArray, clazz: Class<T>): T {
        return mapper.readValue(data, clazz)
    }

    private fun eventBytes(event: Any): ByteArray {
        return mapper.writeValueAsBytes(event)
    }

    private fun eventId(): UUID {
        return UUID.randomUUID()
    }

    private fun eventType(event: Any): String {
        return "(?<=[a-zA-Z])[A-Z]"
            .toRegex()
            .replace(event::class.simpleName!!) { "-${it.value}" }
            .lowercase()
    }
}
