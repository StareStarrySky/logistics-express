package com.dduptop.logistics.server.model.response.xml.insert

import com.dduptop.logistics.server.model.response.xml.Response
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

/**
 * 创建订单（订单下单取号接口）成功返回
 */
class OrderInsertResponse : Response() {
    /**
     * 主单单号,子单单号,...
     */
    @JacksonXmlProperty(localName = "txLogisticID")
    var txLogisticID: String? = null
}
