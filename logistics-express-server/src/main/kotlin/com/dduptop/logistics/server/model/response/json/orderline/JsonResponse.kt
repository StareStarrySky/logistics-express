package com.dduptop.logistics.server.model.response.json.orderline

import com.dduptop.logistics.server.model.common.BaseResponse

class JsonResponse : BaseResponse() {
    /**
     * 接收方标识
     */
    var receiveID: String? = null

    /**
     * 调用接口的执行结果
     */
    var responseState: Boolean? = null

    /**
     * 错误描述信息
     */
    var errorDesc: String? = null

    /**
     * 详细
     */
    var responseItems: List<ResponseItem>? = null
}
