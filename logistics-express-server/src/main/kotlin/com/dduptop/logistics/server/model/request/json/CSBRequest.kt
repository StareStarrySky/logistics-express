package com.dduptop.logistics.server.model.request.json

import com.dduptop.logistics.server.model.common.BaseRequest
import javax.validation.constraints.NotNull

class CSBRequest : BaseRequest() {
    /**
     * 请求签名
     */
    var dataDigest: String? = null

    /**
     * 对接系统标识
     * 如果是快递包裹就传：XXX-YZXB
     * 如果是标快传：XXX-EMS
     */
    @NotNull
    var wpCode: String? = null

    /**
     * 请求报文内容
     */
    @NotNull
    var logisticsInterface: String? = null
}
