package com.example.doctorpractice.scheduling

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "application")
data class AppProperties(
    val tenancy: String,
    val eventStore: EventStore,
    val projectionStore: ProjectionStore
) {
    data class EventStore(
        val url: String
    )

    data class ProjectionStore(
        val url: String
    )
}
