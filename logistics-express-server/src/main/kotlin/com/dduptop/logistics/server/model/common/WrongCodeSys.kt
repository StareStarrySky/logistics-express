package com.dduptop.logistics.server.model.common

enum class WrongCodeSys(val message: String) {
    S01("非法的XML/JSON"),
    S02("非法的数字签名"),
    S03("非法的物流公司/仓储公司"),
    S04("非法的通知类型"),
    S05("非法的通知内空"),
    S06("网络超时，请重试"),
    S07("系统异常，请重试"),
    S08("HTTP 状态异常（非 200）"),
    S09("返回报文为空"),
    S10("找不到对应的网关信息"),
    S11("非法的网关信息"),
    S12("非法的请求参数"),
    S13("业务服务异常");
}
