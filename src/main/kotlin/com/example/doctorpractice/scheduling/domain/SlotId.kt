package com.example.doctorpractice.scheduling.domain

import java.util.*

data class SlotId(val value: UUID) {
    companion object {
        fun create(idGenerator: IdGenerator): SlotId {
            return SlotId(idGenerator.nextUuid())
        }
    }
}
