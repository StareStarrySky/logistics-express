package com.dduptop.logistics

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.dduptop.**", "com.zy.mylib.**"])
class LogisticsExpressApplication

fun main(args: Array<String>) {
    runApplication<LogisticsExpressApplication>(*args)
}
