package com.dduptop.logistics.server.service.impl

import com.dduptop.logistics.server.service.ServiceRunner
import com.dduptop.logistics.server.model.request.xml.BaseXmlRequest
import com.dduptop.logistics.server.model.response.xml.OrderCreateResponse
import com.dduptop.logistics.server.model.response.xml.XmlResponses
import com.dduptop.logistics.server.service.EmsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ServiceCreateOrderRunnerProcessor : ServiceRunner<BaseXmlRequest, XmlResponses<OrderCreateResponse>> {
    @Autowired
    private lateinit var emsService: EmsService

    override fun process(paramType: BaseXmlRequest): XmlResponses<OrderCreateResponse> {
        return emsService.createOrder(paramType)
    }
}
