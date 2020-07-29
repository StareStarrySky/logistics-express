package com.dduptop.logistics.server.model.request.json.classification

import com.dduptop.logistics.server.model.common.BaseRequest

class Classification : BaseRequest() {
    /**
     * 测试：发件/收件地址对 ID，单次请求中唯一
     * 正式：订单号
     */
    lateinit var objectId: String

    /**
     * 发件地址
     */
    lateinit var senderAddress: Address

    /**
     * 收件地址
     */
    lateinit var receiverAddress: Address
}
