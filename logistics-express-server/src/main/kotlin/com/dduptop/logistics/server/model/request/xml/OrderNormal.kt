package com.dduptop.logistics.server.model.request.xml

import com.dduptop.logistics.server.model.common.EcCompanyId
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import javax.validation.constraints.NotNull

/**
 * 创建订单（订单下单取号接口+订单接入接口）
 */
@JacksonXmlRootElement(localName = "OrderNormal")
class OrderNormal {
    /**
     * 订单接入时间 yyyy-mm-dd hh:mm:ss
     */
    @NotNull
    @JacksonXmlProperty(localName = "created_time")
    lateinit var createdTime: String

    /**
     * 物流承运方
     * A：邮务
     * B：速递
     */
    @NotNull
    @JacksonXmlProperty(localName = "logistics_provider")
    var logisticsProvider: Char? = null

    /**
     * 渠道来源标识
     */
    @NotNull
    @JacksonXmlProperty(localName = "ecommerce_no")
    lateinit var ecommerceNo: EcCompanyId

    /**
     * 电商客户标识
     * 与 sender_no 字段不能同时为空
     */
    @NotNull
    @JacksonXmlProperty(localName = "ecommerce_user_id")
    lateinit var ecommerceUserId: String

    /**
     * 物流订单号
     */
    @NotNull
    @JacksonXmlProperty(localName = "logistics_order_no")
    lateinit var ogisticsOrderNo: String

    /**
     * 基础产品代码
     * 1：标准快递 2：快递包裹 3：代收/到付（标准快递）
     */
    @NotNull
    @JacksonXmlProperty(localName = "base_product_no")
    lateinit var baseProductNo: String

    /**
     * 寄件人信息
     */
    @NotNull
    @JacksonXmlProperty(localName = "sender")
    lateinit var sender: Address

    /**
     * 发货人信息
     */
    @JacksonXmlProperty(localName = "pickup")
    lateinit var pickup: Address

    /**
     * 收件人信息
     */
    @NotNull
    @JacksonXmlProperty(localName = "receiver")
    lateinit var receiver: Address

    /**
     * 商品信息
     */
    @NotNull
    @JacksonXmlElementWrapper(localName = "Cargo")
    @JacksonXmlProperty(localName = "cargos")
    lateinit var cargos: List<Cargo>
}
