package com.dduptop.logistics.server.service.impl

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.springframework.stereotype.Component

@Component
class EMSXmlRequest : EMSRequest() {
    final val xmlMapper = XmlMapper()

    init {
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        xmlMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        xmlMapper.configure(SerializationFeature.WRAP_EXCEPTIONS, true)
        xmlMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
    }

    @Throws(JsonProcessingException::class, JsonMappingException::class)
    override fun <T> toBean(content: String?, valueType: Class<T>, vararg parameterClasses: Class<*>?): T {
        val javaType = xmlMapper.typeFactory.constructParametricType(valueType, *parameterClasses)
        return xmlMapper.readValue(content, javaType)
    }
}
