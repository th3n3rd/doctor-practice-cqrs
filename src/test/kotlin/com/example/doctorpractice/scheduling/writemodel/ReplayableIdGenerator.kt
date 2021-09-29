package com.example.doctorpractice.scheduling.writemodel

import com.example.doctorpractice.scheduling.domain.IdGenerator
import java.util.*

class ReplayableIdGenerator : IdGenerator {
    private var recording = true
    private val ids: MutableList<UUID> = mutableListOf()

    override fun nextUuid(): UUID {
        if (recording) {
            val nextId = UUID.randomUUID()
            ids.add(nextId)
            return nextId
        }
        return ids.removeFirst()
    }

    fun stopRecording() {
        recording = false
    }
}
