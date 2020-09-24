package com.dduptop.logistics.server.config.feign

import com.dduptop.micro.service.client.FeignErrorDecoder
import com.dduptop.micro.service.client.FeignOAuth2NonCloudRequestInterceptor
import com.dduptop.service.auth.client.NonCloudAuthClient
import com.dduptop.service.document.client.DocumentClient
import feign.Feign
import feign.Logger
import org.springframework.beans.factory.ObjectFactory
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.openfeign.support.SpringDecoder
import org.springframework.cloud.openfeign.support.SpringEncoder
import org.springframework.cloud.openfeign.support.SpringMvcContract
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.Serializable

@Configuration
@ConfigurationProperties(prefix = "micro-service")
class FeignBeanConfig {
    lateinit var appId: String

    lateinit var secret: String

    lateinit var baseUrl: String

    lateinit var system: String

    var documentServiceConfig = DocumentServiceConfig

    var authServiceConfig = AuthServiceConfig

    @Bean
    fun nonCloudAuthClient(converterObj: ObjectFactory<HttpMessageConverters>,
                           feignOAuth2NonCloudRequestInterceptor: FeignOAuth2NonCloudRequestInterceptor,
                           feignErrorDecoder: FeignErrorDecoder): NonCloudAuthClient {
        return build(authServiceConfig, converterObj, feignOAuth2NonCloudRequestInterceptor, feignErrorDecoder)
    }

    @Bean
    fun documentClient(converterObj: ObjectFactory<HttpMessageConverters>,
                       feignOAuth2NonCloudRequestInterceptor: FeignOAuth2NonCloudRequestInterceptor,
                       feignErrorDecoder: FeignErrorDecoder): DocumentClient {
        return build(documentServiceConfig, converterObj, feignOAuth2NonCloudRequestInterceptor, feignErrorDecoder)
    }

    private inline fun <reified T> build(microServiceConfig: MicroServiceConfig,
                                         converterObj: ObjectFactory<HttpMessageConverters>,
                                         feignOAuth2NonCloudRequestInterceptor: FeignOAuth2NonCloudRequestInterceptor,
                                         feignErrorDecoder: FeignErrorDecoder): T {
        return Feign.builder()
            .requestInterceptor(feignOAuth2NonCloudRequestInterceptor)
            .contract(SpringMvcContract())
            .encoder(SpringEncoder(converterObj))
            .decoder(SpringDecoder(converterObj))
            .errorDecoder(feignErrorDecoder)
            .logger(Logger.JavaLogger(T::class.java))
            .logLevel(Logger.Level.FULL)
            .target(T::class.java, "${baseUrl}${microServiceConfig.url}")
    }

    open class MicroServiceConfig : Serializable {
        companion object {
            private const val SERIAL_VERSION_UID = 414639551756513851L
        }

        lateinit var url: String
    }

    object DocumentServiceConfig : MicroServiceConfig()

    object AuthServiceConfig : MicroServiceConfig()
}
