package com.dduptop.logistics.server.manager

interface logisticsManager {
    /**
     * 下单取号
     */
    fun createOrder()

    /**
     * 运单轨迹信息
     */
    fun orderLine()
}
