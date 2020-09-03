package com.dduptop.logistics.client.not.boot.form

class ClassificationReq : BaseRequest() {
    /**
     * 测试：发件/收件地址对 ID，单次请求中唯一
     * 正式：订单号
     */
    var objectId: String? = null

    /**
     * 发件地址
     */
    var senderClaAddress: ClaAddress? = null

    /**
     * 收件地址
     */
    var receiverClaAddress: ClaAddress? = null
}
