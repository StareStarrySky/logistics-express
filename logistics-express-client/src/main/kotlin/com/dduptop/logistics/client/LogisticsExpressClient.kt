package com.dduptop.logistics.client

import com.dduptop.logistics.client.config.FeignOAuth2RequestInterceptor
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "logisticsExpress", url = "https://this-is-a-placeholder.com/logisticsExpress", contextId = "logisticsExpress", configuration = [FeignOAuth2RequestInterceptor::class, FeignClientsConfiguration::class])
interface LogisticsExpressClient {
    /**
     * 接口 - 登录用户
     * @return 返回token
     */
    @PostMapping("/trust/login")
    fun userLogin(@RequestParam("userId") userId: String): String
}
