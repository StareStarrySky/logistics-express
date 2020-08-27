package com.dduptop.logistics.client.not.boot

import com.dduptop.logistics.client.not.boot.form.*
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.zy.mylib.security.LoginUser
import com.zy.mylib.utils.StringUtils
import org.apache.http.Consts
import org.apache.http.HttpResponse
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

    fun getContent(timestamp: Long, signNonce: String, loginUser: LoginUser): String {
        val content = StringBuilder(1024)
        content.append(appId)
            .append(timestamp)
            .append(signNonce)
            .append(loginUser.telephone)
            .append(loginUser.userId)
            .append(loginUser.userName)
            .append(StringUtils.join(loginUser.roles.toTypedArray(), "", 0))
            .append(loginUser.type)
        return content.toString()
    }

    fun login(timestamp: Long, signNonce: String, sign: String, loginUser: LoginUser): RestMessage {
        val url = "${baseUrl}/auth/login?now=${timestamp}&signNonce=${signNonce}&sign=${sign}&appId=${appId}"
        val result = this.executePostJson(url, loginUser, null)
        return this.toBean(result, RestMessage::class.java)
    }

    fun createOrder(form: OrderNormal, token: String?): RestMessage {
        val url = "${baseUrl}/logistics/create_order"
        val result = this.executePostJson(url, form, token)
        return this.toBean(result, RestMessage::class.java)
    }

    fun classification(req: List<ClassificationReq>, token: String?): CSBResponse<ClassificationRes> {
        val url = "${baseUrl}/logistics/classification"
        val result = this.executePostJson(url, req, token)
        return this.toBean(result, CSBResponse::class.java, ClassificationRes::class.java) as CSBResponse<ClassificationRes>
    }

    fun orderLine(traceNo: String, token: String?): JsonResponse {
        val url = "${baseUrl}/logistics/order_line/${traceNo}"
        val result = this.executeGetJson(url, token)
        return this.toBean(result, JsonResponse::class.java)
    }

    fun batchNo(noCount: Int, token: String?): RestMessage {
        val url = "${baseUrl}/logistics/batch_no?noCount=${noCount}"
        val result = this.executeGetJson(url, token)
        return this.toBean(result, RestMessage::class.java)
    }

    fun orderInsert(form: OrderNormals, token: String?): RestMessage {
        val url = "${baseUrl}/logistics/order_insert"
        val result = this.executePostJson(url, form, token)
        return this.toBean(result, RestMessage::class.java)
    }

    fun printBill(waybillNo: String, form: BillModel, token: String?): ByteArray {
        val url = "${baseUrl}/logistics/print_bill?waybillNo=${waybillNo}"
        val result = this.executePost(url, form, token)
        return EntityUtils.toByteArray(result.entity)
    }

    @Throws(Exception::class)
    private fun executeGetJson(url: String, token: String?): String? {
        val builder = SSLContextBuilder()
        builder.loadTrustMaterial(null) { _, _ -> true }
        val sslsf = SSLConnectionSocketFactory(builder.build())
        val httpClient: HttpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build()
        val get = HttpGet(url)
        get.addHeader("Accept", "application/json")
        get.addHeader("token", token)
        // 30秒超时
        get.config = config
        val response = httpClient.execute(get)
        return EntityUtils.toString(response.entity, Consts.UTF_8.name())
    }

    private fun executePostJson(url: String, apiRequest: Any, token: String?): String? {
        val httpResponse = this.executePost(url, apiRequest, token)
        return EntityUtils.toString(httpResponse.entity, Consts.UTF_8.name())
    }

    @Throws(Exception::class)
    private fun executePost(url: String, apiRequest: Any, token: String?): HttpResponse {
        val json = objectMapper.writeValueAsString(apiRequest)
        val builder = SSLContextBuilder()
        builder.loadTrustMaterial(null) { _, _ -> true }
        val sslsf = SSLConnectionSocketFactory(builder.build())
        val httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build()
        val post = HttpPost(url)
        post.addHeader("Accept", "application/json")
        post.addHeader("token", token)
        val entity = StringEntity(json, ContentType.APPLICATION_JSON)
        post.entity = entity
        // 30秒超时
        post.config = config
        return httpClient.execute(post)
    }

    @Throws(JsonProcessingException::class, JsonMappingException::class)
    fun <T> toBean(content: String?, valueType: Class<T>, vararg parameterClasses: Class<*>?): T {
        val javaType = objectMapper.typeFactory.constructParametricType(valueType, *parameterClasses)
        return objectMapper.readValue(content, javaType)
    }
}
