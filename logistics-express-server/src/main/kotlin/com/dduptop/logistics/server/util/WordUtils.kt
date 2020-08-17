package com.dduptop.logistics.server.util

import com.zy.mylib.base.exception.BusException
import freemarker.template.Configuration
import org.springframework.cglib.beans.BeanMap
import java.io.BufferedWriter
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

object WordUtils {
    fun bean2Map(bean: Any): Map<Any?, Any?> {
        return BeanMap.create(bean).toMap()
    }

    fun templateProcess(data: Map<Any?, Any?>, templateName: String): ByteArray {
        val config = Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS).apply {
            defaultEncoding = StandardCharsets.UTF_8.displayName()
            isClassicCompatible = true
            setClassForTemplateLoading(WordUtils::class.java, "/template")
        }
        val template = config.getTemplate(templateName, StandardCharsets.UTF_8.displayName())

        try {
            ByteArrayOutputStream().use { byteArrayOutputStream ->
                OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8).use { outputStreamWriter ->
                    BufferedWriter(outputStreamWriter).use { bufferedWriter ->
                        template.process(data, bufferedWriter)
                        return byteArrayOutputStream.toByteArray()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw BusException.builder().message(e.message).build()
        }
    }
}
