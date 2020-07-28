package com.dduptop.logistics.server.config.feign

import feign.Feign
import org.springframework.context.annotation.Bean

class FeignCSBClientConfig {
    @Bean
    fun client(): Feign {
        return Feign.builder().client(FeignCSBClient()).build()
    }
}
