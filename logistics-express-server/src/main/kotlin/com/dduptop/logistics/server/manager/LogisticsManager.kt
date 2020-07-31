package com.dduptop.logistics.server.manager

import com.dduptop.logistics.server.model.request.json.classification.ClassificationReq
import com.dduptop.logistics.server.model.request.xml.create.OrderNormal
import com.dduptop.logistics.server.model.response.json.CSBResponse
import com.dduptop.logistics.server.model.response.json.classification.ClassificationRes
import com.dduptop.logistics.server.model.response.json.orderline.JsonResponse
import com.zy.mylib.webmvc.model.RestMessage

interface LogisticsManager {
    /**
     * 下单取号
     */
    fun createOrder(form: OrderNormal): RestMessage

    /**
     * 获取分拣码
     */
    fun classification(req: List<ClassificationReq>): CSBResponse<ClassificationRes>

    /**
     * 运单轨迹信息
     */
    fun orderLine(traceNo: String): JsonResponse
}
