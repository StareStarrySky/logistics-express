package com.dduptop.logistics.server.util

import sun.misc.BASE64Encoder
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets
import java.security.InvalidKeyException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object SignUtils {
    const val HMAC_ALGORITHM = "HmacSHA1"
    var mac = Mac.getInstance("HmacSHA1")

    @Throws(NoSuchAlgorithmException::class, InvalidKeyException::class, UnsupportedEncodingException::class)
    fun sign(origin: String, secret: String): String {
        val signKey = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), "HmacSHA1")
        mac.init(signKey)
        val bytes = mac.doFinal(origin.toByteArray(StandardCharsets.UTF_8))
        return toBase64(bytes)
    }

    fun toBase64(bytes: ByteArray): String {
        return Base64.getEncoder().encodeToString(bytes)
    }

    fun verifySign(sign: String, content: String, secret: String): Boolean {
        return try {
            sign == sign(content, secret)
        } catch (e: Exception) {false}
    }

    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
    fun makeSignEMS(content: String): String {
        return BASE64Encoder().encode(MessageDigest.getInstance("MD5").digest((content).toByteArray(charset("UTF-8"))))
    }
}
