package com.dduptop.logistics.client

import com.dduptop.logistics.client.config.FeignErrorDecoder
import com.dduptop.logistics.client.config.FeignOAuth2RequestInterceptor
import com.dduptop.logistics.client.form.*
import com.zy.mylib.security.LoginUser
import com.zy.mylib.webmvc.model.RestMessage
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.web.bind.annotation.*

@FeignClient(name = "logisticsExpress", url = "\${logistics-express.url}",
    contextId = "logisticsExpress",
    path = "/logistics",
    configuration = [
        FeignOAuth2RequestInterceptor::class,
        FeignClientsConfiguration::class
    ],
    fallback = FeignErrorDecoder::class
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

    @GetMapping("/order_line/{traceNo}")
    fun orderLine(@PathVariable("traceNo") traceNo: String): JsonResponse

    @GetMapping("/batch_no")
    fun batchNo(@RequestParam("noCount") noCount: Int): RestMessage

    @PostMapping("/order_insert")
    fun orderInsert(@RequestBody form: OrderNormals): RestMessage

    @PostMapping("/print_bill")
    fun printBill(@RequestParam("sourceFileType") sourceFileType: String,
                  @RequestBody form: BillModel): ByteArray
}
