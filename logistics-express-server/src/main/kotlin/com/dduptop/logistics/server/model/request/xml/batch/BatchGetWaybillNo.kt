package com.dduptop.logistics.server.model.request.xml.batch

import com.dduptop.logistics.server.model.common.BaseContent
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import javax.validation.constraints.NotNull

/**
 * 批量取号
 */
@JacksonXmlRootElement(localName = "BatchGetWaybillNo")
class BatchGetWaybillNo : BaseContent() {
    /**
     * 请求创建时间 yyyy-mm-dd hh:mm:ss
     */
    @NotNull
    @JacksonXmlProperty(localName = "CreatedTime")
    lateinit var createdTime: String

    /**
     * 客户代码
     * 邮政协议客户代码
     */
    @NotNull
    @JacksonXmlProperty(localName = "CustomerNo")
    lateinit var customerNo: String

    /**
     * 业务种类（可扩展）
     * 6 标准快递
     * 8 代收到付
     * 9 快递包裹
     */
    @NotNull
    @JacksonXmlProperty(localName = "MailType")
    lateinit var mailType: String

    /**
     * 单量（1-1000）
     */
    @NotNull
    @JacksonXmlProperty(localName = "count")
    lateinit var count: String
}
