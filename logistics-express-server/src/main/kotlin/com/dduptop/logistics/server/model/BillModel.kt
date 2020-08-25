package com.dduptop.logistics.server.model

class BillModel {
    /**
     * 产品类型
     * 标准快递
     */
    var productType: String? = null

    /**
     * 时间
     */
    var createTime: String? = null

    /**
     * 分拣码
     */
    var classification: String? = null

    /**
     * 收名
     */
    var receiverName: String? = null

    /**
     * 收机
     */
    var receiverMobile: String? = null

    /**
     * 收址
     */
    var receiverAddress: String? = null

    /**
     * 寄名
     */
    var senderName: String? = null

    /**
     * 寄机
     */
    var senderMobile: String? = null

    /**
     * 寄址
     */
    var senderAddress: String? = null

    /**
     * 付款方式
     */
    var payWay: String? = null

    /**
     * 计价重量
     */
    var payWeight: String? = null

    /**
     * 计价金额
     */
    var money: String? = null

    /**
     * 数量
     */
    var number: String? = null

    /**
     * 重量
     */
    var weight: String? = null

    /**
     * 配货信息
     */
    var goods: String? = null

    /**
     * 备注
     */
    var note: String? = null

    /**
     * 标识
     */
    var sign: String? = null
}
