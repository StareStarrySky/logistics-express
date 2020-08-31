package com.dduptop.logistics.server.model.request.xml.create

import com.dduptop.logistics.server.model.common.BaseContent
import com.dduptop.logistics.server.model.common.EcCompanyId
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import javax.validation.constraints.NotNull

/**
 * 创建订单（订单下单取号接口）
 */
@JacksonXmlRootElement(localName = "OrderNormal")
class OrderNormal : BaseContent() {
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
     * 基础产品代码 A对应2 B对应1
     */
    @NotNull
    @JacksonXmlProperty(localName = "logistics_provider")
    var logisticsProvider: Char? = null

    /**
     * 基础产品代码
     * 1 - 标快 2 - 快包
     */
    @NotNull
    @JacksonXmlProperty(localName = "base_product_no")
    lateinit var baseProductNo: String

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
    var ecommerceUserId: String? = null

    /**
     * 客户类型
     * 0 散户 1 协议客户（默认为 1）
     */
    @JacksonXmlProperty(localName = "sender_type")
    var senderType: Int? = null

    /**
     * 协议客户代码
     * 与 ecommerce_user_id 字段不能同时为空
     */
    @JacksonXmlProperty(localName = "sender_no")
    var senderNo: String? = null

    /**
     * 批次号
     */
    @JacksonXmlProperty(localName = "batch_no")
    var batchNo: String? = null

    /**
     * 运单号(下单取号接口)
     */
    @JacksonXmlProperty(localName = "waybill_no")
    var waybillNo: String? = null

    /**
     * 运单号(订单接入接口)
     */
    @JacksonXmlProperty(localName = "mailNo")
    var mailNo: String? = null

    /**
     * 回单标识
     * 1:基本 2:回执 3:短信 5:电子返单
     * 6:格式返单 7:自备返单 8:反向返单
     */
    @JacksonXmlProperty(localName = "receipt_flag")
    var receiptFlag: Int? = null

    /**
     * 回单运单号
     */
    @JacksonXmlProperty(localName = "receipt_waybill_no")
    var receiptWaybillNo: String? = null

    /**
     * 一票多件标志
     * 0 正常 1 一票多件
     */
    @JacksonXmlProperty(localName = "one_bill_flag")
    var oneBillFlag: String? = null

    /**
     * 内部订单来源标识
     * 0：直接对接
     * 1：邮务国内小包订单系统
     * 2：邮务国际小包订单系统
     * 3：速递国内订单系统
     * 4：速递国际订单系统（shipping）
     * 5：在线发货平台
     * 默认为0
     */
    @NotNull
    @JacksonXmlProperty(localName = "inner_channel")
    var innerChannel: Int? = null

    /**
     * 物流订单号（自定义）
     */
    @NotNull
    @JacksonXmlProperty(localName = "logistics_order_no")
    lateinit var logisticsOrderNo: String

    /**
     * 业务产品分类（可售卖产品代码）(下单取号接口)
     * 1：标准快递 2：快递包裹 3：代收/到付（标准快递）
     */
    @JacksonXmlProperty(localName = "biz_product_no")
    var bizProductNo: String? = null

    /**
     * 业务产品分类（可售卖产品代码）(订单接入接口)
     * 1：标准快递 2：快递包裹 3：代收/到付（标准快递）
     */
    @JacksonXmlProperty(localName = "product_type")
    var productType: String? = null

    /**
     * 项目标识
     * "山西公安户籍（SXGAHJ）
     * 公安网上车管（GAWSCG）
     * 苹果（APPLE）"
     */
    @JacksonXmlProperty(localName = "project_id")
    var projectId: EcCompanyId? = null

    /**
     * 寄件人信息
     */
    @NotNull
    @JacksonXmlProperty(localName = "sender")
    lateinit var sender: OrderAddress

    /**
     * 发货人信息
     */
    @JacksonXmlProperty(localName = "pickup")
    var pickup: OrderAddress? = null

    /**
     * 收件人信息
     */
    @NotNull
    @JacksonXmlProperty(localName = "receiver")
    lateinit var receiver: OrderAddress

    /**
     * 商品信息
     */
    @NotNull
    @JacksonXmlElementWrapper(localName = "cargos")
    @JacksonXmlProperty(localName = "Cargo")
    lateinit var cargos: List<Cargo>
}
