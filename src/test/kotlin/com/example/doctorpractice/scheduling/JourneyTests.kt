package com.example.doctorpractice.scheduling

import com.example.doctorpractice.scheduling.infrastructure.web.DailySchedulePayload
import com.example.doctorpractice.scheduling.infrastructure.web.ListAvailableSlotsPayload
import org.awaitility.kotlin.await
import org.awaitility.kotlin.has
import org.awaitility.kotlin.untilCallTo
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@ActiveProfiles("test")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class JourneyTests(@Autowired val client: WebTestClient) {
    protected fun scheduleDay(doctorId: UUID, date: LocalDate, slots: List<Pair<LocalTime, Duration>>) {
        client
            .post()
            .uri("/daily-schedules")
            .bodyValue(
                DailySchedulePayload(
                    doctorId,
                    date,
                    slots.map {
                        DailySchedulePayload.Slot(
                            it.first,
                            it.second
                        )
                    }
                )
            )
            .exchange()
            .expectStatus().isCreated
    }

    private fun listAvailableSlots(date: LocalDate): ListAvailableSlotsPayload {
        return client
            .get()
            .uri("/slots/$date/available")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(ListAvailableSlotsPayload::class.java)
            .returnResult()
            .responseBody!!
    }

    protected fun assertSlotIsAvailable(doctorId: UUID, date: LocalDate, startTime: LocalTime, duration: Duration) {
        assertDoesNotThrow {
            awaitUntilSlotIsAvailable(date, doctorId, startTime, duration)
        }
    }

    private fun awaitUntilSlotIsAvailable(
        date: LocalDate,
        doctorId: UUID,
        startTime: LocalTime,
        duration: Duration
    ) {
        await untilCallTo { listAvailableSlots(date) } has {
            slots.getOrDefault(doctorId, emptyList()).any {
                it.date == date &&
                    it.startTime == startTime &&
                    it.duration == duration
            }
        }
    }
}
