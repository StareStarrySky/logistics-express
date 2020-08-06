package com.dduptop.logistics.server.service.impl

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "ems.batch-no")
class ServiceBatchNoRunnerConfig {
    lateinit var url: String

    lateinit var key: String
}
