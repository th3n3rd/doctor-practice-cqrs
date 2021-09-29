package com.example.doctorpractice.scheduling

import com.example.doctorpractice.scheduling.fixtures.eightAm
import com.example.doctorpractice.scheduling.fixtures.eightPm
import com.example.doctorpractice.scheduling.fixtures.tenMinutes
import com.example.doctorpractice.scheduling.fixtures.today
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.*

class DoctorJourneyTests(@Autowired client: WebTestClient) : JourneyTests(client) {
    private val doctorFoo = UUID.randomUUID()

    @Test
    fun `a journey`() {
        scheduleDay(
            doctorFoo, today,
            listOf(
                eightAm to tenMinutes,
                eightPm to tenMinutes
            )
        )
        assertSlotIsAvailable(doctorFoo, today, eightAm, tenMinutes)
        assertSlotIsAvailable(doctorFoo, today, eightPm, tenMinutes)
    }
}
