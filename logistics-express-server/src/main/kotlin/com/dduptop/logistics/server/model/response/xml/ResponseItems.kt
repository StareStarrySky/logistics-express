package com.dduptop.logistics.server.model.response.xml

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

class ResponseItems<T : Response> {
    @JacksonXmlProperty(localName = "response")
    var response: T? = null
}
