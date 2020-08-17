package com.dduptop.logistics.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class, HibernateJpaAutoConfiguration::class])
@ComponentScan(basePackages = ["com.dduptop.**", "com.zy.mylib.**"])
@EnableFeignClients(basePackages = ["com.dduptop.**", "com.zy.mylib.**"])
class LogisticsExpressApplication

fun main(args: Array<String>) {
    runApplication<LogisticsExpressApplication>(*args)
}
