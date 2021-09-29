package com.example.doctorpractice.scheduling

import com.example.doctorpractice.scheduling.application.commands.CommandDispatcher
import com.example.doctorpractice.scheduling.application.commands.CommandHandlers
import com.example.doctorpractice.scheduling.application.projections.AvailableSlots
import com.example.doctorpractice.scheduling.application.projections.Projection
import com.example.doctorpractice.scheduling.application.queries.QueryDispatcher
import com.example.doctorpractice.scheduling.application.queries.QueryHandlers
import com.example.doctorpractice.scheduling.domain.DailyScheduleRepository
import com.example.doctorpractice.scheduling.domain.IdGenerator
import com.example.doctorpractice.scheduling.domain.RandomIdGenerator
import com.example.doctorpractice.scheduling.domain.queries.AvailableSlotsRepository
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(AppProperties::class)
class AppConfig {
    @Bean
    fun idGenerator(): IdGenerator {
        return RandomIdGenerator()
    }

    @Bean
    fun commandDispatcher(repository: DailyScheduleRepository, idGenerator: IdGenerator): CommandDispatcher {
        return CommandDispatcher(
            CommandHandlers(
                repository,
                idGenerator
            )
        )
    }

    @Bean
    fun queryDispatcher(repository: AvailableSlotsRepository): QueryDispatcher {
        return QueryDispatcher(
            QueryHandlers(
                repository
            )
        )
    }

    @Bean
    fun availableSlotsProjection(repository: AvailableSlotsRepository): Projection {
        return AvailableSlots(repository)
    }
}
