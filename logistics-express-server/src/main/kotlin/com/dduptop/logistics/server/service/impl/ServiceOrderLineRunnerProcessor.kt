package com.dduptop.logistics.server.service.impl

import com.dduptop.logistics.server.model.request.json.MsgContent
import com.dduptop.logistics.server.model.request.json.OrderLine
import com.dduptop.logistics.server.model.response.json.JsonResponse
import com.dduptop.logistics.server.service.EmsService
import com.dduptop.logistics.server.service.ServiceRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ServiceOrderLineRunnerProcessor : ServiceRunner<MsgContent<OrderLine>, JsonResponse> {
    @Autowired
    private lateinit var emsService: EmsService

    override fun process(paramType: MsgContent<OrderLine>): JsonResponse {
        return emsService.orderLine(paramType)
    }
}
