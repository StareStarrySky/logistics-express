package com.dduptop.logistics.server.config.feign

import feign.Feign
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignCSBClientConfig {
    @Bean
    fun client(): Feign {
        return Feign.builder().client(FeignCSBClient()).build()
    }
}
