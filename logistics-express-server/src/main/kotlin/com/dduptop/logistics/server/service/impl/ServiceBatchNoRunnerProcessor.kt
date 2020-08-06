package com.dduptop.logistics.server.service.impl

import com.dduptop.logistics.server.model.request.xml.BaseXmlRequest
import com.dduptop.logistics.server.model.response.xml.batch.BatchNoResponses
import com.dduptop.logistics.server.service.EMSService
import com.dduptop.logistics.server.service.ServiceRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.URI

@Service
class ServiceBatchNoRunnerProcessor : ServiceRunner<BaseXmlRequest, BatchNoResponses> {
    @Autowired
    private lateinit var emsService: EMSService

    @Autowired
    private lateinit var emsXmlRequest: EMSXmlRequest

    @Autowired
    private lateinit var batchNo: ServiceBatchNoRunnerConfig

    override fun process(paramType: BaseXmlRequest): BatchNoResponses {
        val result = emsService.xmlRequest(URI(batchNo.url), paramType)
        return emsXmlRequest.toBean(result, BatchNoResponses::class.java)
    }
}
