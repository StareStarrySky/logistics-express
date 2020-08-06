package com.dduptop.logistics.server.rest

import com.dduptop.logistics.server.manager.LogisticsManager
import com.dduptop.logistics.server.model.request.json.classification.ClassificationReq
import com.dduptop.logistics.server.model.request.xml.create.OrderNormal
import com.dduptop.logistics.server.model.response.json.CSBResponse
import com.dduptop.logistics.server.model.response.json.classification.ClassificationRes
import com.dduptop.logistics.server.model.response.json.orderline.JsonResponse
import com.zy.mylib.webmvc.base.BaseRest
import com.zy.mylib.webmvc.model.RestMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/logistics")
class LogisticsRest : BaseRest() {
    @Autowired
    private lateinit var logisticsManager: LogisticsManager

    @PostMapping("/create_order")
    fun createOrder(form: OrderNormal): RestMessage {
        return logisticsManager.createOrder(form)
    }

    @PostMapping("/classification")
    fun classification(req: List<ClassificationReq>): CSBResponse<ClassificationRes> {
        return logisticsManager.classification(req)
    }

    @GetMapping("/order_line/{traceNo}")
    fun orderLine(@PathVariable("traceNo") traceNo: String): JsonResponse {
        return logisticsManager.orderLine(traceNo)
    }

    @GetMapping("/batch_no")
    fun batchNo(@RequestParam("noCount") noCount: Int): RestMessage {
        return logisticsManager.batchNo(noCount)
    }
}
