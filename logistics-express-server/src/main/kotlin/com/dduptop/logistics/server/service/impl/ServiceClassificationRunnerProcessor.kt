package com.dduptop.logistics.server.service.impl

import com.dduptop.logistics.server.model.request.json.CSBRequest
import com.dduptop.logistics.server.model.response.json.CSBResponse
import com.dduptop.logistics.server.model.response.json.Classification
import com.dduptop.logistics.server.service.CSBService
import com.dduptop.logistics.server.service.ServiceRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "ems.classification")
class ServiceClassificationRunnerProcessor : ServiceRunner<CSBRequest, CSBResponse<Classification>> {
    lateinit var url: String

    lateinit var api: String

    lateinit var version: String

    lateinit var accessKey: String

    lateinit var secretKey: String

    @Autowired
    private lateinit var csbService: CSBService

    @Autowired
    private lateinit var emsJsonRequest: EMSJsonRequest

    override fun process(paramType: CSBRequest): CSBResponse<Classification> {
        val classification = csbService.classification(paramType, api, version, accessKey, secretKey)
        return emsJsonRequest.toBean(classification, CSBResponse::class.java, Classification::class.java) as CSBResponse<Classification>
    }
}
