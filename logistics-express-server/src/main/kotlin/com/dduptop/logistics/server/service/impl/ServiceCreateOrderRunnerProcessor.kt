package com.dduptop.logistics.server.service.impl

import com.dduptop.logistics.server.service.ServiceRunner
import com.dduptop.logistics.server.model.request.xml.BaseXmlRequest
import com.dduptop.logistics.server.model.response.xml.create.OrderCreateResponse
import com.dduptop.logistics.server.model.response.xml.XmlResponses
import com.dduptop.logistics.server.service.EMSService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.URI

@Service
class ServiceCreateOrderRunnerProcessor : ServiceRunner<BaseXmlRequest, XmlResponses<OrderCreateResponse>> {
    @Autowired
    private lateinit var emsService: EMSService
    @Autowired
    private lateinit var emsXmlRequest: EMSXmlRequest

    @Autowired
    private lateinit var createOrder: ServiceCreateOrderRunnerConfig

    override fun process(paramType: BaseXmlRequest): XmlResponses<OrderCreateResponse> {
        val result = emsService.createOrder(URI(createOrder.url), paramType)
        return emsXmlRequest.toBean(result, XmlResponses::class.java, OrderCreateResponse::class.java) as XmlResponses<OrderCreateResponse>
    }
}
