package com.dduptop.logistics.server.model.common

/**
 * 消息类型
 */
enum class MsgType {
    /**
     * 订购服务申请
     */
    SUBSCRIPTION_APPLY,

    /**
     * 服务审核
     */
    SUBSCRIPTION_AUDIT,

    /**
     * 创建订单（订单下单取号接口+订单接入接口）
     */
    ORDERCREATE,

    /**
     * 白名单订阅接口
     */
    ORDER_WHITE_LIST,

    /**
     * 订单更新
     */
    ORDERUPDATE,

    /**
     * 轨迹接口编码
     */
    whzcwyh_JDPT_TRACE
}
