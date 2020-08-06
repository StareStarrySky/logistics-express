package com.dduptop.logistics.server.service.impl

import com.dduptop.logistics.server.model.common.BaseContent
import com.dduptop.logistics.server.model.common.EcCompanyId
import com.dduptop.logistics.server.model.common.MsgType
import com.dduptop.logistics.server.model.request.xml.BaseXmlRequest
import com.dduptop.logistics.server.util.SignUtils
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.zy.mylib.base.exception.BusException
import org.springframework.stereotype.Component

@Component
class EMSXmlRequest : EMSRequest() {
    private final val xmlMapper = XmlMapper()

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

    fun buildReq(content: BaseContent?, salt: String, msgType: MsgType): BaseXmlRequest {
        val xml = try {
            xmlMapper.writeValueAsString(content)
        } catch (e: Exception) {
            e.printStackTrace()
            throw BusException.builder().httpStatus(500).message(e.message).build()
        }
//        val xmlFull = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>$xml"
//        val digest = Base64Utils.encodeToUrlSafeString(HashUtils.getMd5("$xmlFull$parentId").toByteArray(StandardCharsets.UTF_8))
        val digest = SignUtils.makeSignEMS("$xml$salt")
        return BaseXmlRequest().apply {
            ecCompanyId = EcCompanyId.whzcwyh
            msg_type = msgType
            logistics_interface = xml
            data_digest = digest
        }
    }
}
