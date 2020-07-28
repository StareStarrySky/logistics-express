package com.dduptop.logistics.server.service

import com.dduptop.logistics.client.config.FeignOAuth2RequestInterceptor
import com.dduptop.logistics.client.config.FeignSSLConfig
import com.dduptop.logistics.server.config.feign.FeignFormConfig
import com.dduptop.logistics.server.model.request.json.MsgContent
import com.dduptop.logistics.server.model.request.json.OrderLine
import com.dduptop.logistics.server.model.request.xml.BaseXmlRequest
import com.dduptop.logistics.server.model.response.json.JsonResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(name = "ems", contextId = "ems", url = "\${ems.create-order-url}",
    configuration = [FeignOAuth2RequestInterceptor::class,
        FeignClientsConfiguration::class,
        FeignSSLConfig::class,
        FeignFormConfig::class
    ]
)
interface EMSService {
    /**
     * 创建订单（订单下单取号接口+订单接入接口）
     */
    @PostMapping(consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun createOrder(xmlRequest: BaseXmlRequest): String?

    /**
     * 运单轨迹信息
     */
    @PostMapping(consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE], produces = [MediaType.APPLICATION_XML_VALUE])
    fun orderLine(msgContent: MsgContent<OrderLine>): JsonResponse?
}
