package com.example.doctorpractice.scheduling.domain

import java.util.*

interface IdGenerator {
    fun nextUuid(): UUID
}
