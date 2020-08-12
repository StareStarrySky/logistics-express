package com.dduptop.logistics.client.not.boot.form

/**
 * 商品信息
 */
class Cargo {
    /**
     * 商品名称,内件名称
     */
    lateinit var cargoName: String

    /**
     * 订单号,用于一票多件
     */
    var orderNo: String? = null
}
