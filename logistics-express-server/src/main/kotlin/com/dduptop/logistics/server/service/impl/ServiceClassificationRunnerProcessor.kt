package com.dduptop.logistics.server.service.impl

import com.dduptop.logistics.server.model.request.json.CSBRequest
import com.dduptop.logistics.server.model.response.json.CSBResponse
import com.dduptop.logistics.server.model.response.json.classification.ClassificationRes
import com.dduptop.logistics.server.service.CSBService
import com.dduptop.logistics.server.service.ServiceRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ServiceClassificationRunnerProcessor : ServiceRunner<CSBRequest, CSBResponse<ClassificationRes>> {
    @Autowired
    private lateinit var csbService: CSBService

    @Autowired
    private lateinit var emsJsonRequest: EMSJsonRequest

    @Autowired
    private lateinit var classification: ServiceClassificationRunnerConfig

    override fun process(paramType: CSBRequest): CSBResponse<ClassificationRes> {
        val classification = csbService.classification(paramType, classification.api, classification.version, classification.accessKey, classification.secretKey)
        return emsJsonRequest.toBean(classification, CSBResponse::class.java, ClassificationRes::class.java) as CSBResponse<ClassificationRes>
    }
}
