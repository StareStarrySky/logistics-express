package com.dduptop.logistics.server.model.response.xml

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

/**
 * 响应
 */
@JacksonXmlRootElement(localName = "responses")
class XmlResponses<T : Response> {
    @JacksonXmlProperty(localName = "responseItems")
    lateinit var responseItems: ResponseItems<T>
}
