package com.dduptop.logistics.server.model.request.xml.insert

import com.dduptop.logistics.server.model.common.BaseContent
import com.dduptop.logistics.server.model.request.xml.create.OrderNormal
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import javax.validation.constraints.NotNull

@JacksonXmlRootElement(localName = "OrderNormals")
class OrderNormals : BaseContent() {
    @NotNull
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "OrderNormal")
    lateinit var orderNormals: List<OrderNormal>
}
