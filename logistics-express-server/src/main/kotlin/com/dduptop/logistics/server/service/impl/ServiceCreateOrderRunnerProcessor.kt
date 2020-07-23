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
    @Autowired
    private lateinit var emsRequest: EmsRequest

    override fun process(paramType: BaseXmlRequest): XmlResponses<OrderCreateResponse> {
        val result =  emsService.createOrder(paramType)
        return emsRequest.xml2Bean(result, XmlResponses::class.java, OrderCreateResponse::class.java) as XmlResponses<OrderCreateResponse>
    }
}
