package com.example.doctorpractice.scheduling.application.projections

interface Projection {
    fun project(event: Any)
}
