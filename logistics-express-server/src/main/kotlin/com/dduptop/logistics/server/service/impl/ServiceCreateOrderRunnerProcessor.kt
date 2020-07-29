package com.dduptop.logistics.server.service.impl

import com.dduptop.logistics.server.service.ServiceRunner
import com.dduptop.logistics.server.model.request.xml.BaseXmlRequest
import com.dduptop.logistics.server.model.response.xml.create.OrderCreateResponse
import com.dduptop.logistics.server.model.response.xml.XmlResponses
import com.dduptop.logistics.server.service.EMSService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Service
import java.net.URI

@Service
@ConfigurationProperties(prefix = "ems.create-order")
class ServiceCreateOrderRunnerProcessor : ServiceRunner<BaseXmlRequest, XmlResponses<OrderCreateResponse>> {
    @Autowired
    private lateinit var emsService: EMSService
    @Autowired
    private lateinit var emsXmlRequest: EMSXmlRequest

    lateinit var url: String

    override fun process(paramType: BaseXmlRequest): XmlResponses<OrderCreateResponse> {
        val result = emsService.createOrder(URI(url), paramType)
        return emsXmlRequest.toBean(result, XmlResponses::class.java, OrderCreateResponse::class.java) as XmlResponses<OrderCreateResponse>
    }
}
