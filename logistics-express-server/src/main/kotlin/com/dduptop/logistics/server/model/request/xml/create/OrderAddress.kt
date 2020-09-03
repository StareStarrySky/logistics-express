package com.dduptop.logistics.server.model.request.xml.create

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import javax.validation.constraints.NotNull

/**
 * 参与人信息
 */
class OrderAddress {
    /**
     * 用户姓名
     */
    @NotNull
    @JacksonXmlProperty(localName = "name")
    var name: String? = null

    /**
     * 用户邮编
     */
    @JacksonXmlProperty(localName = "post_code")
    var postCode: String? = null

    /**
     * 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
     */
    @JacksonXmlProperty(localName = "phone")
    var phone: String? = null

    /**
     * 用户移动电话
     */
    @NotNull
    @JacksonXmlProperty(localName = "mobile")
    var mobile: String? = null

    /**
     * 用户所在省，使用国标全称
     */
    @NotNull
    @JacksonXmlProperty(localName = "prov")
    var prov: String? = null

    /**
     * 用户所在市，使用国标全称
     */
    @NotNull
    @JacksonXmlProperty(localName = "city")
    var city: String? = null

    /**
     * 用户所在县（区），使用国标全称
     */
    @JacksonXmlProperty(localName = "county")
    var county: String? = null

    /**
     * 用户详细地址
     */
    @NotNull
    @JacksonXmlProperty(localName = "address")
    var address: String? = null
}
