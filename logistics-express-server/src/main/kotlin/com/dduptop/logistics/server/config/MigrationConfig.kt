package com.dduptop.logistics.server.config

import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import javax.sql.DataSource

@Configuration
class MigrationConfig {
    @Bean
    fun flywayInitializer(flyway: Flyway): FlywayMigrationInitializer {
        return FlywayMigrationInitializer(flyway) { }
    }

    @Bean
    fun flywayConfig(dataSource: DataSource): Flyway {
        return Flyway.configure()
            .dataSource(dataSource)
            .baselineOnMigrate(true)
            .baselineVersion("0")
            .ignoreMissingMigrations(true)
            .validateOnMigrate(false)
            .cleanOnValidationError(true)
            .load()
    }

    @Bean
    @DependsOn("entityManagerFactory")
    fun delayedFlywayInitializer(flyway: Flyway): FlywayMigrationInitializer {
        return FlywayMigrationInitializer(flyway, null)
    }
}
