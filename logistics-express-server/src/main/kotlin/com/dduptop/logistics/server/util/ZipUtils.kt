package com.dduptop.logistics.server.util

import com.zy.mylib.base.exception.BusException
import org.springframework.core.io.ClassPathResource
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

object ZipUtils {
    fun toDocx(templateName: String, newEntry: Map<String, ByteArray>): ByteArray {
        val classPathResource = ClassPathResource("/template/${templateName}")
        try {
            ByteArrayOutputStream().use { byteArrayOutputStream ->
                ZipOutputStream(byteArrayOutputStream, StandardCharsets.UTF_8).use { zos ->
                    ZipInputStream(classPathResource.inputStream, StandardCharsets.UTF_8).use { zis ->
                        filterZipEntry(zis, zos, newEntry)
                        return byteArrayOutputStream.toByteArray()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw BusException.builder().message(e.message).build()
        }
    }

    @Throws(IOException::class)
    fun filterZipEntry(zis: ZipInputStream, zos: ZipOutputStream, newEntry: Map<String, ByteArray>) {
        var entry: ZipEntry?
        while (zis.nextEntry.also { entry = it } != null) {
            if (!newEntry.keys.contains(entry!!.name)) {
                zos.putNextEntry(ZipEntry(entry!!.name))
                zos.write(zis.readBytes())
                zos.closeEntry()
            }
        }
        newEntry.forEach { (entryName, byteArray) -> addZipEntry(entryName, byteArray, zos) }
        zos.finish()
    }

    @Throws(IOException::class)
    fun addZipEntry(entryName: String, byteArray: ByteArray, zos: ZipOutputStream) {
        val entry = ZipEntry(entryName)
        zos.putNextEntry(entry)
        zos.write(byteArray)
    }
}
