package com.even.common.request.interceptor

import android.text.TextUtils
import com.even.common.utils.LogUtils
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.StringBuilder

/**
 * @author  Created by Even on 2019/8/8
 *  Email: emailtopan@163.com
 *  日志统一打印格式
 */
class LogInterceptor : HttpLoggingInterceptor.Logger {
    var mMessage: StringBuilder = StringBuilder()

    override fun log(message: String) {
        if (message.startsWith("--> POST") || message.startsWith("--> GET")) {
            mMessage.setLength(0)
            mMessage.append(" ")
            mMessage.append("\r\n")
        }
        if (message.startsWith("{") && message.endsWith("}")
            || message.startsWith("[") && message.endsWith("]")
        ) {
            mMessage.append(formatJson(message))
        }
        mMessage.append(message.plus("\n"))
        //请求或响应结束，打印格式化日志
        if (message.startsWith("<-- END HTTP")) {
            LogUtils.i(mMessage.toString())
        }
    }

    /**
     * 格式化json字符串
     *
     * @param jsonStr 需要格式化的字符串
     * @return 已格式化的字符串
     */
    private fun formatJson(jsonStr: String): String {
        if (TextUtils.isEmpty(jsonStr)) return ""
        val sb = StringBuilder()
        var last = '\u0000'
        var current = '\u0000'
        var index = 0
        for (i in 0 until jsonStr.length) {
            last = current
            current = jsonStr[i]
            //遇见{[ 进行换行，并在下一行缩进
            when (current) {
                '{', '[' -> {
                    sb.append(current)
                    sb.append("\n")
                    index++
                    addIndent(sb, index)
                }
                //遇到]},当前行进行缩进
                ']', '}' -> {
                    sb.append("\n")
                    index--
                    addIndent(sb, index)
                    sb.append(current)
                }
                //遇到，换行
                ',' -> {
                    sb.append(current)
                    if (last != '\\') {
                        sb.append("\n")
                        addIndent(sb, index)
                    }
                }
                else -> sb.append(current)
            }
        }
        return sb.toString()
    }

    /**
     * 设置缩进，添加制表符
     */
    private fun addIndent(sb: StringBuilder, index: Int) {
        for (i in 0 until index) {
            sb.append("\t")
        }
    }
}