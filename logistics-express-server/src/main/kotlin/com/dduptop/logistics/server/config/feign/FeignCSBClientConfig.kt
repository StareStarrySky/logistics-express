package com.dduptop.logistics.server.config.feign

import feign.Client
import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration

//@Configuration
class FeignCSBClientConfig {
    @Bean
    fun client(): Client {
        return FeignCSBClient()
    }
}
