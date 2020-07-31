package com.dduptop.logistics.client

import com.dduptop.logistics.client.config.FeignOAuth2RequestInterceptor
import com.dduptop.logistics.client.form.*
import com.zy.mylib.security.LoginUser
import com.zy.mylib.webmvc.model.RestMessage
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.web.bind.annotation.*

@FeignClient(name = "logisticsExpress", url = "https://this-is-a-placeholder.com/logisticsExpress",
    contextId = "logisticsExpress",
    configuration = [
        FeignOAuth2RequestInterceptor::class,
        FeignClientsConfiguration::class
    ]
)
interface LogisticsExpressClient {
    /**
     * 接口 - 登录用户
     * @return 返回token
     */
    @PostMapping("/login")
    fun login(@RequestParam("now") timestamp: Long, @RequestParam("signNonce") signNonce: String,
              @RequestParam("sign") sign: String, @RequestParam("appId") appId: String,
              @RequestBody loginUser: LoginUser): RestMessage

    @PostMapping("/create_order")
    fun createOrder(form: OrderNormal): RestMessage

    @PostMapping("/classification")
    fun classification(req: List<ClassificationReq>): CSBResponse<ClassificationRes>

    @GetMapping("/create_order/{traceNo}")
    fun createOrder(@PathVariable("traceNo") traceNo: String): JsonResponse
}
