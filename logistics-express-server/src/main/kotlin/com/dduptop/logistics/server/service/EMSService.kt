package com.dduptop.logistics.server.service

import com.dduptop.logistics.server.config.feign.FeignFormConfig
import com.dduptop.logistics.server.config.feign.FeignSSLConfig
import com.dduptop.logistics.server.model.request.json.orderline.MsgContent
import com.dduptop.logistics.server.model.request.xml.BaseXmlRequest
import com.dduptop.micro.service.client.FeignErrorDecoder
import com.dduptop.micro.service.client.FeignOAuth2RequestInterceptor
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import java.net.URI

@FeignClient(name = "ems", contextId = "ems", url = "https://this-is-a-placeholder.com/",
    configuration = [FeignClientsConfiguration::class,
        FeignSSLConfig::class,
        FeignFormConfig::class
    ],
    fallback = FeignErrorDecoder::class
)
interface EMSService {
    /**
     * 创建订单（订单下单取号接口）
     * 批量取号
     */
    @PostMapping(consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun xmlRequest(uri: URI, xmlRequest: BaseXmlRequest): String?

    /**
     * 运单轨迹信息
     */
    @PostMapping(consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun orderLine(uri: URI, msgContent: MsgContent): String?
}
