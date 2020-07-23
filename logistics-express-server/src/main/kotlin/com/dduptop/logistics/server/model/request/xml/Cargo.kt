package com.dduptop.logistics.server.model.request.xml

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
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
    lateinit var cargoName: String

    /**
     * 订单号,用于一票多件
     */
    @JacksonXmlProperty(localName = "order_no")
    var orderNo: String? = null
}
