package com.even.common.request.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author  Created by Even on 2019/8/8
 *  Email: emailtopan@163.com
 *  添加请求头拦截器
 */
class HeaderInterceptor : Interceptor {
    var headerMaps: MutableMap<String, Any> = mutableMapOf()

    constructor() {
        headerMaps["platform"] = "android"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        if (headerMaps.isNotEmpty()) {
            headerMaps.forEach { request.addHeader(it.key, it.value.toString()) }
        }
        return chain.proceed(request.build())
    }
}