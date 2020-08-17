package com.dduptop.logistics.server.config.feign

import feign.Client
import feign.httpclient.ApacheHttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.conn.ssl.TrustSelfSignedStrategy
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.ssl.SSLContextBuilder
import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration

//@Configuration
class FeignSSLConfig {
    @Bean
    fun feignClient(): Client {
        return ApacheHttpClient(getHttpClient())
    }

    private fun getHttpClient(): CloseableHttpClient {
        val timeout = 10000
        return try {
            val sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(TrustSelfSignedStrategy()).build()
            val config: RequestConfig = RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout)
                .build()
            HttpClientBuilder
                .create()
                .useSystemProperties()
                .setDefaultRequestConfig(config)
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier())
                .build()
        } catch (e: Exception) {
            throw RuntimeException()
        }
    }
}
