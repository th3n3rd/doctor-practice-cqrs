package com.example.doctorpractice.scheduling.domain

import java.util.*

class RandomIdGenerator : IdGenerator {
    override fun nextUuid(): UUID = UUID.randomUUID()
}
