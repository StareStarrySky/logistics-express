package com.dduptop.logistics.server.model.common

enum class WrongCodeBus(val message: String) {
    B00("未知业务错误"),
    B01("关键字段缺失"),
    B02("关键数据格式不正确"),
    B03("没有找到请求的数据"),
    B04("数据保存失败"),
    B05("号段获取失败");
}
