package com.example.doctorpractice.scheduling.infrastructure.eventstoredb

import com.eventstore.dbclient.EventStoreDBClient
import com.eventstore.dbclient.EventStoreDBConnectionString
import com.example.doctorpractice.scheduling.AppProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EsClientConfig(private val properties: AppProperties) {

    @Bean
    fun esClient(): EventStoreDBClient {
        return EventStoreDBClient.create(
            EventStoreDBConnectionString.parseOrThrow(
                properties.eventStore.url
            )
        )
    }

    @Bean
    fun esStreamContext(): EsStreamContext {
        return EsStreamContext(properties.tenancy)
    }
}
