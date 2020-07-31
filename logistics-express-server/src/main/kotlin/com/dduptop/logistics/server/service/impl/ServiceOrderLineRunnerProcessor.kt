package com.dduptop.logistics.server.service.impl

import com.dduptop.logistics.server.model.request.json.orderline.MsgContent
import com.dduptop.logistics.server.model.response.json.orderline.JsonResponse
import com.dduptop.logistics.server.service.EMSService
import com.dduptop.logistics.server.service.ServiceRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.URI

@Service
class ServiceOrderLineRunnerProcessor : ServiceRunner<MsgContent, JsonResponse> {
    @Autowired
    private lateinit var emsService: EMSService
    @Autowired
    private lateinit var emsJsonRequest: EMSJsonRequest

    @Autowired
    private lateinit var orderLine: ServiceOrderLineRunnerConfig

    override fun process(paramType: MsgContent): JsonResponse {
        val result = emsService.orderLine(URI(orderLine.url), paramType)
        return emsJsonRequest.toBean(result, JsonResponse::class.java)
    }
}
