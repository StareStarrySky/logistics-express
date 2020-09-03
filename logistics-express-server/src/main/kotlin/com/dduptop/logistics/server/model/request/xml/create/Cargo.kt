package com.dduptop.logistics.server.model.request.xml.create

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import javax.validation.constraints.NotNull

/**
 * 商品信息
 */
class Cargo {
    /**
     * 商品名称,内件名称
     */
    @NotNull
    @JacksonXmlProperty(localName = "cargo_name")
    var cargoName: String? = null

    /**
     * 订单号,用于一票多件
     */
    @JacksonXmlProperty(localName = "order_no")
    var orderNo: String? = null
}
