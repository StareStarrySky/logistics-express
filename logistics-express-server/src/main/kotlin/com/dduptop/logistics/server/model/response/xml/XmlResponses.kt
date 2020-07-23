package com.dduptop.logistics.server.model.response.xml

import com.dduptop.logistics.server.model.common.BaseResponse
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

/**
 * 响应
 */
@JacksonXmlRootElement(localName = "responses")
class XmlResponses<T : Response> : BaseResponse() {
    @JacksonXmlProperty(localName = "responseItems")
    var responseItems: ResponseItems<T>? = null
}
