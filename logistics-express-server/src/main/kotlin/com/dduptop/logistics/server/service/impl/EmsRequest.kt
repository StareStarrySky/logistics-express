package com.dduptop.logistics.server.service.impl

import com.dduptop.logistics.server.model.common.BaseRequest
import com.dduptop.logistics.server.model.common.BaseResponse
import com.dduptop.logistics.server.model.common.EcCompanyId
import com.dduptop.logistics.server.model.common.MsgType
import com.dduptop.logistics.server.model.request.json.MsgContent
import com.dduptop.logistics.server.model.request.xml.BaseXmlRequest
import com.dduptop.logistics.server.model.request.xml.XmlRequestContent
import com.dduptop.logistics.server.model.response.xml.OrderCreateResponse
import com.dduptop.logistics.server.model.response.xml.XmlResponses
import com.dduptop.logistics.server.service.EmsService
import com.dduptop.logistics.server.service.ServiceRunner
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.zy.mylib.base.exception.BusException
import com.zy.mylib.utils.DateUtils
import com.zy.mylib.utils.HashUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*

@Component
class EmsRequest {
    private val parternID = ""
    private val xmlMapper = XmlMapper()

    init {
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        xmlMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        xmlMapper.configure(SerializationFeature.WRAP_EXCEPTIONS, true)
        xmlMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
    }

    fun buildXmlRequest(content: XmlRequestContent<*>): BaseXmlRequest {
        val xml = try {
            xmlMapper.writeValueAsString(content.logisticsInterface)
        } catch (e: Exception) {
            e.printStackTrace()
            throw BusException.builder().httpStatus(500).build()
        }
        val xmlFull = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>$xml"
        println("发送请求：$xmlFull")
        val digest = Base64Utils.encodeToUrlSafeString(HashUtils.getMd5("$xmlFull$parternID").toByteArray(StandardCharsets.UTF_8))
        return BaseXmlRequest().apply {
            ecCompanyId = EcCompanyId.DKH
            msgType = MsgType.ORDERCREATE
            logisticsInterface = URLEncoder.encode(xmlFull, StandardCharsets.UTF_8)
            dataDigest = URLEncoder.encode(digest, StandardCharsets.UTF_8)
        }
    }

    fun <PT : BaseRequest, RT : BaseResponse> sendRequest(runner: ServiceRunner<PT, RT>, xmlRequest: PT): RT {
        return runner.process(xmlRequest)
    }

    fun <T> getReqParam(body: T): MsgContent<T> {
        return MsgContent<T>().apply {
            sendId = EcCompanyId.JDPT.name
            msgKind = "JDPT_XXX_TRACE"
            serialNo = UUID.randomUUID().toString()
            sendDate = DateUtils.getToday("yyyyMMddHHmmss")
            dataType = "1"
            msgBody = body
        }
    }
}
