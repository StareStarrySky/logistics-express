package com.dduptop.logistics.server.service.impl

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import org.springframework.stereotype.Component

@Component
class EMSJsonRequest : EMSRequest() {
    final val jsonMapper = JsonMapper()

    init {
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        jsonMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        jsonMapper.configure(SerializationFeature.WRAP_EXCEPTIONS, true)
        jsonMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
    }

    @Throws(JsonProcessingException::class, JsonMappingException::class)
    override fun <T> toBean(content: String?, valueType: Class<T>, vararg parameterClasses: Class<*>?): T {
        val javaType = jsonMapper.typeFactory.constructParametricType(valueType, *parameterClasses)
        return jsonMapper.readValue(content, javaType)
    }
}
