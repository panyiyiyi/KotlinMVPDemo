package com.even.common.utils

import okhttp3.internal.and
import java.io.*

/**
 * @author  Created by Even on 2019/8/14
 *  Email: emailtopan@163.com
 *  对象序列化工具类
 */
object ObjectSerializationUtils {

    /**
     * 序列化
     */
    fun serialization(obj: Serializable): String? {
        if (null == obj) {
            return null
        }
        val arrayOS = ByteArrayOutputStream()
        val objectOS = ObjectOutputStream(arrayOS)
        objectOS.writeObject(obj)
        objectOS.close()
        return encodeBytes(arrayOS.toByteArray())
    }

    /**
     * 反序列化
     */
    fun deserialization(str: String): Any? {
        if (str.isEmpty()) return null
        val byteIs = ByteArrayInputStream(decodeBytes(str))
        val objIs = ObjectInputStream(byteIs)
        return objIs.readObject()
    }

    private fun encodeBytes(bytes: ByteArray): String {
        val strBuf = StringBuffer()
        bytes.forEach {
            strBuf.append(
                (it.toInt().shr(4) and 0xF).plus('a'.toInt()).toChar()
            )
            strBuf.append((it and 0xF).plus('a'.toInt()).toChar())
        }
        return strBuf.toString()
    }

    /**
     * 解码
     */
    private fun decodeBytes(str: String): ByteArray {
        val bytes = ByteArray(str.length / 2)
        var i = 0
        while (i < str.length) {
            var c = str[i]
            bytes[i / 2] = (c - 'a' shl 4).toByte()
            c = str[i + 1]
            bytes[i / 2] = (bytes[i / 2] + (c - 'a')).toByte()
            i += 2
        }
        return bytes
    }

}