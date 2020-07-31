package com.dduptop.logistics.server.service.impl

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "ems.order-line")
class ServiceOrderLineRunnerConfig {
    lateinit var url: String

    lateinit var appKey: String
}
