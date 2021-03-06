package com.dduptop.logistics.server.model.common

/**
 * 消息类型
 */
enum class MsgType {
    /**
     * 创建订单（订单下单取号接口+订单接入接口）
     */
    ORDERCREATE,

    /**
     * 轨迹接口编码
     */
    whzcwyh_JDPT_TRACE,

    /**
     * 批量取号
     */
    BatchGetWaybillNo
}
