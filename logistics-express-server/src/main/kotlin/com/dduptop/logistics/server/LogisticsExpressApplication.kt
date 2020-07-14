package com.dduptop.logistics.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication(scanBasePackages = ["com.dduptop.**", "com.zy.mylib.**"])
@ComponentScan(basePackages = ["com.dduptop.**", "com.zy.mylib.**"])
@EnableFeignClients
class LogisticsExpressApplication

fun main(args: Array<String>) {
    runApplication<LogisticsExpressApplication>(*args)
}
