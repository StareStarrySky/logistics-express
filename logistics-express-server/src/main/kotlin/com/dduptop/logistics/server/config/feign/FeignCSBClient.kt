package com.dduptop.logistics.server.config.feign

import com.alibaba.csb.sdk.HttpCaller
import com.alibaba.csb.sdk.HttpParameters
import com.fasterxml.jackson.databind.json.JsonMapper
import feign.Client
import feign.Request
import feign.Response
import java.nio.charset.StandardCharsets

class FeignCSBClient : Client {

    override fun execute(request: Request, options: Request.Options): Response {
        val headers = request.headers()
        val body = request.requestBody().asString()
        val jsonMapper = JsonMapper()
        val javaType = jsonMapper.typeFactory.constructParametricType(Map::class.java, String::class.java, String::class.java)
        val params = jsonMapper.readValue<Map<String, String>>(body, javaType)

        val builder = HttpParameters.newBuilder()

        builder.requestURL(request.url())
            .method(request.httpMethod().name)
            .api(headers["api"]?.first())
            .version(headers["version"]?.first())
            .accessKey(headers["accessKey"]?.first())
            .secretKey(headers["secretKey"]?.first())

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
