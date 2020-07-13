package com.dduptop.logistics.server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.RedisTemplate
import java.io.Serializable

@Configuration
class RedisConfig {
    @Bean
    fun jwtTokenRedisOperations(redisConnectionFactory: RedisConnectionFactory): RedisOperations<String, out Serializable> {
        val redisTemplate: RedisTemplate<String, out Serializable> = RedisTemplate()
        redisTemplate.setConnectionFactory(redisConnectionFactory)
        return redisTemplate
    }
}
