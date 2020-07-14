package com.dduptop.logistics.server.config

import feign.Logger
import feign.codec.Encoder
import feign.form.spring.SpringFormEncoder
import org.springframework.beans.factory.ObjectFactory
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.support.SpringEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignFormConfig {
    @Bean
    fun encoder(converterObj: ObjectFactory<HttpMessageConverters>): Encoder {
        return SpringFormEncoder(SpringEncoder(converterObj))
    }

    @Bean
    fun level(): Logger.Level {
        return Logger.Level.FULL
    }
}
