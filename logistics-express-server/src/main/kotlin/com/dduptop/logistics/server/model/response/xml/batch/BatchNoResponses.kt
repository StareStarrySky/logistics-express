package com.dduptop.logistics.server.model.response.xml.batch

import com.dduptop.logistics.server.model.common.BaseResponse
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "responses")
class BatchNoResponses : BaseResponse() {
    /**
     * 接收结果
     * true/flase
     */
    @JacksonXmlProperty(localName = "result")
    var result: Boolean? = null

    /**
     * 客户代码
     * 邮政协议客户代码
     */
    @JacksonXmlProperty(localName = "CustomerNo")
    var customerNo: String? = null

    /**
     * 错误信息
     */
    @JacksonXmlProperty(localName = "errorMsg")
    var errorMsg: String? = null

    /**
     * 错误编码
     */
    @JacksonXmlProperty(localName = "errorCode")
    var errorCode: String? = null

    /**
     * 邮件列表
     */
    @JacksonXmlProperty(localName = "waybill_no")
    var waybillNo: String? = null
}
