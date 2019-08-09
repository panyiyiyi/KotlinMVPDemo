package com.even.common.utils

import android.util.Log

/**
 * @author  Created by Even on 2019/8/8
 *  Email: emailtopan@163.com
 *  日志工具类
 */
object LogUtils {
    /**
     * log单次打印的长度
     */
    const val LOG_LENGTH = 2000
    /**
     * 是否显示日志
     */
    var mIsShowLog = false
    /**
     * 日志tag
     */
    var mLogTag = "Even"

    fun init(isShow: Boolean) {
        mIsShowLog = isShow
    }

    fun init(isShow: Boolean, logTag: String) {
        mIsShowLog = isShow
        mLogTag = logTag
    }

    fun i(any: Any) {
        if (mIsShowLog) {
            val msg = any.toString()
            if (msg.length > LOG_LENGTH) {
                val length = msg.length
                for (i in 0 until length step LOG_LENGTH) {
                    if (i.plus(LOG_LENGTH) < length) {
                        Log.i(mLogTag, msg.substring(i, i.plus(LOG_LENGTH)))
                    } else {
                        Log.i(mLogTag, msg.substring(i, length))
                    }
                }
            } else {
                Log.i(mLogTag, msg)
            }
        }
    }

    fun e(any: Any) {
        val msg = any.toString()
        if (msg.length > LOG_LENGTH) {
            val length = msg.length
            for (i in 1..length step LOG_LENGTH) {
                if (i.plus(LOG_LENGTH) < length) {
                    Log.e(mLogTag, msg.substring(i, i.plus(LOG_LENGTH)))
                } else {
                    Log.e(mLogTag, msg.substring(i, length))
                }
            }
        } else {
            Log.e(mLogTag, msg)
        }
    }
}