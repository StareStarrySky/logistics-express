package com.dduptop.logistics.server.service.impl

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "ems.classification")
class ServiceClassificationRunnerConfig {
    lateinit var url: String

    lateinit var api: String

    lateinit var version: String

    lateinit var accessKey: String

    lateinit var secretKey: String

    lateinit var wpCode: String
}
