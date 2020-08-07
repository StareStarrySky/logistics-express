package com.dduptop.logistics.server.service.impl

import com.dduptop.logistics.server.service.ServiceRunner
import com.dduptop.logistics.server.model.request.xml.BaseXmlRequest
import com.dduptop.logistics.server.model.response.xml.insert.OrderInsertResponse
import com.dduptop.logistics.server.model.response.xml.XmlResponses
import com.dduptop.logistics.server.service.EMSService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.URI

@Service
class ServiceOrderInsertRunnerProcessor : ServiceRunner<BaseXmlRequest, XmlResponses<OrderInsertResponse>> {
    @Autowired
    private lateinit var emsService: EMSService

    @Autowired
    private lateinit var emsXmlRequest: EMSXmlRequest

    @Autowired
    private lateinit var orderInsert: ServiceOrderInsertRunnerConfig

    override fun process(paramType: BaseXmlRequest): XmlResponses<OrderInsertResponse> {
        val result = emsService.xmlRequest(URI(orderInsert.url), paramType)
        return emsXmlRequest.toBean(result, XmlResponses::class.java, OrderInsertResponse::class.java) as XmlResponses<OrderInsertResponse>
    }
}
