package com.dduptop.logistics.server.service

import com.dduptop.logistics.client.config.FeignErrorDecoder
import com.dduptop.logistics.client.config.FeignOAuth2RequestInterceptor
import com.dduptop.logistics.client.config.FeignSSLConfig
import com.dduptop.logistics.server.config.feign.FeignFormConfig
import com.dduptop.logistics.server.model.request.json.orderline.MsgContent
import com.dduptop.logistics.server.model.request.xml.BaseXmlRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import java.net.URI

@FeignClient(name = "ems", contextId = "ems", url = "https://this-is-a-placeholder.com/",
    configuration = [FeignOAuth2RequestInterceptor::class,
        FeignClientsConfiguration::class,
        FeignSSLConfig::class,
        FeignFormConfig::class
    ],
    fallback = FeignErrorDecoder::class
)
interface EMSService {
    /**
     * 创建订单（订单下单取号接口+订单接入接口）
     */
    @PostMapping(consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun createOrder(uri: URI, xmlRequest: BaseXmlRequest): String?

    /**
     * 运单轨迹信息
     */
    @PostMapping(consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun orderLine(uri: URI, msgContent: MsgContent): String?
}
