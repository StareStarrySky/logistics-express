package com.dduptop.logistics.client.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.zy.mylib.base.exception.BusException
import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Configuration

@Configuration
open class FeignErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String, response: Response): Exception? {
        return try {
            val json = response.body().asReader().readText()
            val map = ObjectMapper().readValue(json, Map::class.java)
            BusException.builder()
                .httpStatus(response.status())
                .code(map["code"] as String?)
                .message(map["message"] as String?).build()
        } catch (ignored: Exception) {
            BusException.builder().message("未知错误").build()
        }
    }
}
