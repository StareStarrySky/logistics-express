package com.dduptop.logistics.config

import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn

@Configuration
class FlywayConfig {
    @Bean
    fun flywayMigrationInitializer(flyway: Flyway): FlywayMigrationInitializer {
        return FlywayMigrationInitializer(flyway) { }
    }

    @Bean
    @DependsOn("entityManagerFactory")
    fun delayedFlywayInitializer(flyWay: Flyway): FlywayMigrationInitializer {
        return FlywayMigrationInitializer(flyWay, null)
    }
}
