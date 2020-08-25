package com.dduptop.logistics.server.util

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.oned.Code128Writer
import com.zy.mylib.base.exception.BusException
import java.awt.image.BufferedImage
import java.io.IOException
import java.io.OutputStream
import java.nio.charset.StandardCharsets
import javax.imageio.ImageIO

object BarCodeUtils {
    fun barCode(content: String, width: Int, height: Int, customWord: String, os: OutputStream) {
        val bufferedImage = barCode(content, width, height, customWord)
        barCode(bufferedImage, "png", os)
    }

    fun barCode(bufferedImage: BufferedImage, formatName: String, os: OutputStream) {
        try {
            os.use {
                ImageIO.write(bufferedImage, formatName, it)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            throw BusException.builder().message(e.message).build()
        }
    }

    fun barCode(content: String, width: Int, height: Int): BufferedImage {
        try {
            val hints = hashMapOf(EncodeHintType.CHARACTER_SET to StandardCharsets.UTF_8.displayName())
            val code128Writer = Code128Writer()
            val encode = code128Writer.encode(content, BarcodeFormat.CODE_128, width, height, hints)
            return MatrixToImageWriter.toBufferedImage(encode)
        } catch (e: WriterException) {
            e.printStackTrace()
            throw BusException.builder().message(e.message).build()
        }
    }

    fun barCode(content: String, width: Int, height: Int, customWord: String): BufferedImage {
        val barCode = barCode(content, width, height)
        val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

        val graphics2D = bufferedImage.createGraphics()
        graphics2D.drawImage(barCode, 0, 0, barCode.width, barCode.height, null)
        val wordWidth = graphics2D.fontMetrics.stringWidth(customWord)
        val wordX = (width - wordWidth) / 2
        val wordY = height + 20
        graphics2D.drawString(customWord, wordX, wordY)
        graphics2D.dispose()
        bufferedImage.flush()
        return bufferedImage
    }
}
