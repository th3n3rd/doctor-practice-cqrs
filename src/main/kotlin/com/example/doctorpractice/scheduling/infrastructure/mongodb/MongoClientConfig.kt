package com.example.doctorpractice.scheduling.infrastructure.mongodb

import com.example.doctorpractice.scheduling.AppProperties
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration

@Configuration
class MongoClientConfig(properties: AppProperties) : AbstractMongoClientConfiguration() {
    private val connectionString = ConnectionString(properties.projectionStore.url)

    override fun createMongoClient(settings: MongoClientSettings): MongoClient {
        return MongoClients.create(
            MongoClientSettings
                .builder(settings)
                .applyConnectionString(connectionString)
                .build()
        )
    }

    override fun getDatabaseName(): String {
        return connectionString.database!!
    }
}
