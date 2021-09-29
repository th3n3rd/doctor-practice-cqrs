package com.example.doctorpractice.scheduling.infrastructure.eventstoredb

import com.example.doctorpractice.scheduling.infrastructure.common.logger
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Component
class EsAsyncSubscriptionManager(val subscriptionManager: EsSubscriptionManager) {
    private val logger = logger()

    @Bean("subscriptionsThreadPool")
    fun subscriptionsThreadPool(): Executor {
        return Executors.newFixedThreadPool(1)
    }

    @EventListener(ApplicationReadyEvent::class)
    @Async("subscriptionsThreadPool")
    fun start() {
        subscriptionManager.start()
        logger.info("Subscription manager thread started on [subscriptionsThreadPool]")
    }
}
