package com.dduptop.logistics.server.service.impl

import com.dduptop.logistics.server.model.request.json.MsgContent
import com.dduptop.logistics.server.model.request.json.OrderLine
import com.dduptop.logistics.server.model.response.json.JsonResponse
import com.dduptop.logistics.server.service.EMSService
import com.dduptop.logistics.server.service.ServiceRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Service
import java.net.URI

@Service
@ConfigurationProperties(prefix = "ems.order-line")
class ServiceOrderLineRunnerProcessor : ServiceRunner<MsgContent<OrderLine>, JsonResponse> {
    @Autowired
    private lateinit var emsService: EMSService
    @Autowired
    private lateinit var emsXmlRequest: EMSXmlRequest

    lateinit var url: String

    lateinit var appKey: String

    override fun process(paramType: MsgContent<OrderLine>): JsonResponse {
        val result = emsService.orderLine(URI(url), paramType)
        println(result)
        return emsXmlRequest.toBean(result, JsonResponse::class.java)
    }
}
