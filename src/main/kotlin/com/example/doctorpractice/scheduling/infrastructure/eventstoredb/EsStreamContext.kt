package com.example.doctorpractice.scheduling.infrastructure.eventstoredb

class EsStreamContext(private val tenancy: String) {
    fun withPrefix(streamName: String = "") = "[$tenancy]$streamName"
}
