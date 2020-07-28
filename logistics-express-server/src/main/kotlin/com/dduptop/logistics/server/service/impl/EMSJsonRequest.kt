package com.dduptop.logistics.server.service.impl

import com.dduptop.logistics.server.model.common.EcCompanyId
import com.dduptop.logistics.server.model.request.json.MsgContent
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.zy.mylib.utils.DateUtils
import com.zy.mylib.utils.HashUtils
import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*

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

    fun <T> getReqParam(body: T, appKey: String): MsgContent<T> {
        val json = jsonMapper.writeValueAsString(body)
        val digest = Base64Utils.encodeToUrlSafeString(HashUtils.getMd5("$json$appKey").toByteArray(StandardCharsets.UTF_8))
        return MsgContent<T>().apply {
            sendId = EcCompanyId.whzcwyh
            proviceNo = "99"
            msgKind = EcCompanyId.whzcwyh_JDPT_TRACE
            serialNo = UUID.randomUUID().toString()
            sendDate = DateUtils.getToday("yyyyMMddHHmmss")
            receiveId = EcCompanyId.JDPT
            dataType = "1"
            dataDigest = digest
            msgBody = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
        }
    }
}
