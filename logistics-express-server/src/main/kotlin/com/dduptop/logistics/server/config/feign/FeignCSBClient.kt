package com.dduptop.logistics.server.config.feign

import com.alibaba.csb.sdk.HttpCaller
import com.alibaba.csb.sdk.HttpParameters
import com.dduptop.logistics.server.util.SignUtils
import feign.Client
import feign.Request
import feign.Response
//import org.springframework.boot.context.properties.ConfigurationProperties
//import org.springframework.stereotype.Component
//import java.io.ByteArrayInputStream
//import java.io.FileInputStream
//import java.io.InputStream
import java.nio.charset.StandardCharsets

//@Component
//@ConfigurationProperties(prefix = "ems.classification")
class FeignCSBClient : Client {
//    /**
//     * 服务名
//     */
//    private lateinit var api: String
//
//    /**
//     * 版本号
//     */
//    private lateinit var version: String
//
//    /**
//     * accessKey
//     */
//    private lateinit var accessKey: String
//
//    /**
//     * secretKey
//     */
//    private lateinit var secretKey: String

    override fun execute(request: Request, options: Request.Options): Response {
        val body = request.requestBody().asString()

        val builder = HttpParameters.newBuilder()

        builder.requestURL(request.url())
            .method(request.httpMethod().name)
            .api("routInfoQueryForPDD")
            .version("1.0.0")
            .accessKey("dd979a77b2a44e54b65f01dcbc0bae04")
            .secretKey("kCZfusS4Zn300MTnYtIlWzhdoE8=")

        val params = hashMapOf("dataDigest" to SignUtils.makeSignEMS("json"), "wpCode" to "whzcwyh-EMS", "logisticsInterface" to "json")
        builder.putParamsMapAll(params)

//        val httpReturn = HttpCaller.invokeReturn(builder.build())
        val res = HttpCaller.invoke(builder.build())

        return Response.builder()
            .status(200)
            .reason(null)
            .headers(null)
            .request(request)
            .body(res, StandardCharsets.UTF_8)
            .build()
    }
}
