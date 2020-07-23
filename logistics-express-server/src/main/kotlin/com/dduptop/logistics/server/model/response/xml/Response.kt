package com.dduptop.logistics.server.model.response.xml

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

open class Response {
    /**
     * 是否成功
     */
    @JacksonXmlProperty(localName = "success")
    var success: Boolean? = null

    /**
     * 失败原因
     */
    @JacksonXmlProperty(localName = "reason")
    var reason: String? = null
}
