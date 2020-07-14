package com.dduptop.logistics.server.model.response.json

class JsonResponse {
    /**
     * 接收方标识
     */
    lateinit var receiveID: String

    /**
     * 调用接口的执行结果
     */
    var responseState: Boolean? = null

    /**
     * 错误描述信息
     */
    lateinit var errorDesc: String

    /**
     * 详细
     */
    lateinit var responseItems: List<ResponseItem>
}
