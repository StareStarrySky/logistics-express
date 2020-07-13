package com.dduptop.logistics.config

import com.zy.mylib.security.LoginUser
import com.zy.mylib.security.Passport
import com.zy.mylib.security.casbin.EnforcerManager
import com.zy.mylib.security.casbin.jwt.impl.AuthzFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.request.RequestContextListener

@Configuration
class OAuthConfig {
    @Bean
    fun authzFilter(passport: Passport<LoginUser>, enforcerManager: EnforcerManager<*>): FilterRegistrationBean<*> {
        return FilterRegistrationBean<AuthzFilter>().apply {
            filter = AuthzFilter().apply {
                this.enforcerManager = enforcerManager
                this.passport = passport
            }
            order = 1
            addUrlPatterns("/*")
        }
    }

    @Bean
    fun requestContextListener(): RequestContextListener {
        return RequestContextListener()
    }
}
