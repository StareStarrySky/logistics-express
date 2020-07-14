package com.dduptop.logistics.server.model.request.xml

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import javax.validation.constraints.NotNull

/**
 * 参与人信息
 */
class Address {
    /**
     * 用户姓名
     */
    @NotNull
    @JacksonXmlProperty(localName = "name")
    lateinit var name: String

    /**
     * 用户移动电话
     */
    @NotNull
    @JacksonXmlProperty(localName = "mobile")
    lateinit var mobile: String

    /**
     * 用户所在省，使用国标全称
     */
    @NotNull
    @JacksonXmlProperty(localName = "prov")
    lateinit var prov: String

    /**
     * 用户所在市，使用国标全称
     */
    @NotNull
    @JacksonXmlProperty(localName = "city")
    lateinit var city: String

    /**
     * 用户所在县（区），使用国标全称
     */
    @JacksonXmlProperty(localName = "county")
    lateinit var county: String

    /**
     * 用户详细地址
     */
    @NotNull
    @JacksonXmlProperty(localName = "address")
    lateinit var address: String
}
