package com.dduptop.logistics.client.not.boot

import com.dduptop.logistics.client.not.boot.form.*
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.zy.mylib.security.LoginUser
import com.zy.mylib.webmvc.model.RestMessage
import org.apache.http.Consts
import org.apache.http.client.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContextBuilder
import org.apache.http.util.EntityUtils

class LogisticsExpressNotBootClient(var baseUrl: String, var appId: String, var secret: String) {
    private val objectMapper = ObjectMapper()
    private val config = RequestConfig.custom().setConnectTimeout(30 * 1000)
        .setConnectionRequestTimeout(30 * 1000).setSocketTimeout(30 * 1000).build()

    fun login(timestamp: Long, signNonce: String, sign: String, loginUser: LoginUser): RestMessage {
        val url = "${baseUrl}/logistics?now=${timestamp}&signNonce=${signNonce}&sign=${sign}&appId=${appId}"
        val result = this.executePost(url, loginUser)
        return this.toBean(result, RestMessage::class.java)
    }

    fun createOrder(form: OrderNormal): RestMessage {
        val url = "${baseUrl}/logistics/create_order"
        val result = this.executePost(url, form)
        return this.toBean(result, RestMessage::class.java)
    }

    fun classification(req: List<ClassificationReq>): CSBResponse<ClassificationRes> {
        val url = "${baseUrl}/logistics/classification"
        val result = this.executePost(url, req)
        return this.toBean(result, CSBResponse::class.java, ClassificationRes::class.java) as CSBResponse<ClassificationRes>
    }

    fun orderLine(traceNo: String): JsonResponse {
        val url = "${baseUrl}/logistics/order_line/${traceNo}"
        val result = this.executeGet(url)
        return this.toBean(result, JsonResponse::class.java)
    }

    fun batchNo(noCount: Int): RestMessage {
        val url = "${baseUrl}/logistics/batch_no?${noCount}"
        val result = this.executeGet(url)
        return this.toBean(result, RestMessage::class.java)
    }

    fun orderInsert(form: OrderNormals): RestMessage {
        val url = "${baseUrl}/logistics/order_insert"
        val result = this.executePost(url, form)
        return this.toBean(result, RestMessage::class.java)
    }

    @Throws(JsonProcessingException::class, JsonMappingException::class)
    fun <T> toBean(content: String?, valueType: Class<T>, vararg parameterClasses: Class<*>?): T {
        val javaType = objectMapper.typeFactory.constructParametricType(valueType, *parameterClasses)
        return objectMapper.readValue(content, javaType)
    }

    @Throws(Exception::class)
    private fun executeGet(url: String): String? {
        val builder = SSLContextBuilder()
        builder.loadTrustMaterial(null) { _, _ -> true }
        val sslsf = SSLConnectionSocketFactory(builder.build())
        val httpClient: HttpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build()
        val get = HttpGet(url)
        get.addHeader("Accept", "application/json")
        // 30秒超时
        get.config = config
        val response = httpClient.execute(get)
        return EntityUtils.toString(response.entity, Consts.UTF_8.name())
    }

    @Throws(Exception::class)
    private fun executePost(url: String, apiRequest: Any): String? {
        val json = objectMapper.writeValueAsString(apiRequest)
        val builder = SSLContextBuilder()
        builder.loadTrustMaterial(null) { _, _ -> true }
        val sslsf = SSLConnectionSocketFactory(builder.build())
        val httpClient: HttpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build()
        val post = HttpPost(url)
        post.addHeader("Accept", "application/json")
        val entity = StringEntity(json, ContentType.APPLICATION_JSON)
        post.entity = entity
        // 30秒超时
        post.config = config
        val response = httpClient.execute(post)
        return EntityUtils.toString(response.entity, Consts.UTF_8.name())
    }
}
