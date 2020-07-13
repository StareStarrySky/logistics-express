package com.dduptop.logistics.client.config

import com.zy.mylib.base.exception.BusException
import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest

@Configuration
open class FeignOAuth2RequestInterceptor : RequestInterceptor {
    companion object {
        private const val HEADER_TOKEN_KEY = "token"
    }

    protected open fun getRequest(): HttpServletRequest {
        val attrs = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
            ?: throw BusException.builder().message("当前线程中不存在 Request 上下文").build()
        return attrs.request
    }

    protected open fun getToken(): String? {
        return getRequest().getHeader(HEADER_TOKEN_KEY)
    }

    override fun apply(request: RequestTemplate) {
        request.header(HEADER_TOKEN_KEY, getToken())
    }
}
