package com.dduptop.logistics.client.form

/**
 * 创建订单（订单下单取号接口）
 */
class OrderNormal : BaseContent() {
    /**
     * 订单接入时间 yyyy-mm-dd hh:mm:ss
     */
    var createdTime: String? = null

    /**
     * 物流承运方
     * A：邮务
     * B：速递
     * 基础产品代码 A对应2 B对应1
     */
    var logisticsProvider: Char? = null

    /**
     * 基础产品代码
     * 1 - 标快 2 - 快包
     */
    var baseProductNo: String? = null

    /**
     * 渠道来源标识
     */
    var ecommerceNo: EcCompanyId? = null

    /**
     * 电商客户标识
     * 与 sender_no 字段不能同时为空
     */
    var ecommerceUserId: String? = null

    /**
     * 客户类型
     * 0 散户 1 协议客户（默认为 1）
     */
    var senderType: Int? = null

    /**
     * 协议客户代码
     * 与 ecommerce_user_id 字段不能同时为空
     */
    var senderNo: String? = null

    /**
     * 批次号
     */
    var batchNo: String? = null

    /**
     * 运单号(下单取号接口)
     */
    var waybillNo: String? = null

    /**
     * 运单号(订单接入接口)
     */
    var mailNo: String? = null

    /**
     * 一票多件标志
     * 0 正常 1 一票多件
     */
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
    var innerChannel: Int? = null

    /**
     * 物流订单号（自定义）
     */
    var logisticsOrderNo: String? = null

    /**
     * 业务产品分类（可售卖产品代码）(下单取号接口)
     * 1：标准快递 2：快递包裹 3：代收/到付（标准快递）
     */
    var bizProductNo: String? = null

    /**
     * 业务产品分类（可售卖产品代码）(订单接入接口)
     * 1：标准快递 2：快递包裹 3：代收/到付（标准快递）
     */
    var productType: String? = null

    /**
     * 项目标识
     * "山西公安户籍（SXGAHJ）
     * 公安网上车管（GAWSCG）
     * 苹果（APPLE）"
     */
    var projectId: EcCompanyId? = null

    /**
     * 寄件人信息
     */
    lateinit var sender: OrderAddress

    /**
     * 发货人信息
     */
    var pickup: OrderAddress? = null

    /**
     * 收件人信息
     */
    lateinit var receiver: OrderAddress

    /**
     * 商品信息
     */
    lateinit var cargos: List<Cargo>
}
