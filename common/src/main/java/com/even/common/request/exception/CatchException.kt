package com.even.common.request.exception

import com.even.common.utils.LogUtils
import java.io.PrintWriter
import java.io.StringWriter

/**
 * @author  Created by Even on 2019/8/13
 *  Email: emailtopan@163.com
 *  异常日志打印
 */
class CatchException : Thread.UncaughtExceptionHandler {
    val handler: Thread.UncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        LogUtils.e(readExceptionInfo(e))
        handler.uncaughtException(t, e)
    }

    private fun readExceptionInfo(e: Throwable?): String {
        if (null == e) {
            return ""
        }
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        e.printStackTrace(printWriter)
        var cause = e.cause
        while (null != cause) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.flush()
        stringWriter.flush()
        printWriter.close()
        stringWriter.close()
        return stringWriter.toString()
    }

}