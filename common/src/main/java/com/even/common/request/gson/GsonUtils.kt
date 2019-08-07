package com.even.common.request.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * @author  Created by Even on 2019/8/7
 *  Email: emailtopan@163.com
 *  gson解析工具类，防止异常
 */
object GsonUtils {
    fun buildGson(): Gson {
        return GsonBuilder().registerTypeAdapter(Int::class.java, IntDefaultAdapter())
            .registerTypeAdapter(Double::class.java, DoubleDefaultAdapter())
            .registerTypeAdapter(Long::class.java, LongDefaultAdapter())
            .create()
    }
}