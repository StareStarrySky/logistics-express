package com.dduptop.logistics.client.not.boot.form

class ClassificationReq : BaseRequest() {
    /**
     * 测试：发件/收件地址对 ID，单次请求中唯一
     * 正式：订单号
     */
    lateinit var objectId: String

    /**
     * 发件地址
     */
    lateinit var senderClaAddress: ClaAddress

    /**
     * 收件地址
     */
    lateinit var receiverClaAddress: ClaAddress
}
