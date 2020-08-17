package com.dduptop.logistics.server.service

import com.dduptop.logistics.server.config.feign.FeignCSBClientConfig
import com.dduptop.logistics.server.model.request.json.CSBRequest
import com.dduptop.micro.service.client.FeignErrorDecoder
import com.dduptop.micro.service.client.FeignOAuth2RequestInterceptor
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "ems", contextId = "csb", url = "\${ems.classification.url}",
    configuration = [FeignOAuth2RequestInterceptor::class,
        FeignClientsConfiguration::class,
        FeignCSBClientConfig::class
    ],
    fallback = FeignErrorDecoder::class
)
interface CSBService {
    /**
     * 获取分拣码
     */
    @PostMapping
    fun classification(@RequestBody csbRequest: CSBRequest,
                       @RequestHeader("api") api: String,
                       @RequestHeader("version") version: String,
                       @RequestHeader("accessKey") accessKey: String,
                       @RequestHeader("secretKey") secretKey: String): String?
}
