package com.dduptop.logistics.server.service

import com.dduptop.logistics.client.config.FeignOAuth2RequestInterceptor
import com.dduptop.logistics.client.config.FeignSSLConfig
import com.dduptop.logistics.server.config.feign.FeignCSBClientConfig
import com.dduptop.logistics.server.model.request.json.CSBRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "ems", contextId = "csb", url = "\${ems.classification.url}",
    configuration = [FeignOAuth2RequestInterceptor::class,
        FeignClientsConfiguration::class,
        FeignSSLConfig::class,
        FeignCSBClientConfig::class
    ]
)
interface CSBService {
    /**
     * 获取分拣码
     */
    @PostMapping
    fun classification(@RequestBody csbRequest: CSBRequest, api: String, version: String, accessKey: String, secretKey: String): String?
}
