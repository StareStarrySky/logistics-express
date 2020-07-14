package com.dduptop.logistics.server.model.request.json

import javax.validation.constraints.NotNull

/**
 * 运单轨迹消息体
 */
class OrderLine {
    /**
     * 运单号
     */
    @NotNull
    lateinit var traceNo: String
}
