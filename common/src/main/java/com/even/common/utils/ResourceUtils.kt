package com.even.common.utils

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.Character.isSpace
import java.nio.charset.Charset

/**
 *  @author  Created by Even on 2019/11/5
 *  Email: emailtopan@163.com
 *  获取资源工具类
 */
object ResourceUtils {
    private const val BUFFER_SIZE = 8192

    /**
     * 将asset文件转成String
     */
    fun getAssets2String(assetsFilePath: String): String? {
        return getAssets2String(assetsFilePath, "UTF-8")
    }

    /**
     * 将asset文件转成String
     */
    fun getAssets2String(assetsFilePath: String, charsetName: String): String? {
        var inputStream: InputStream? = null
        try {
            inputStream = ApplicationUtils.getInstance().applicationContext.assets.open(assetsFilePath)

            val inputStream2Bytes = inputStream2Bytes(inputStream)
            if (inputStream2Bytes != null) {
                return if (charsetName.trim().isEmpty()) {
                    String(inputStream2Bytes)
                } else {
                    String(inputStream2Bytes, Charset.forName(charsetName))
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return ""
    }

    /**
     * 流转成字节
     */
    fun inputStream2Bytes(inputStream: InputStream): ByteArray? {
        var os: ByteArrayOutputStream? = null
        try {
            os = ByteArrayOutputStream()
            val b = ByteArray(BUFFER_SIZE)
            var len = inputStream.read(b, 0, BUFFER_SIZE)
            while (len != -1) {
                os.write(b, 0, len)
                len = inputStream.read(b, 0, BUFFER_SIZE)
            }
            return os.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                os?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}