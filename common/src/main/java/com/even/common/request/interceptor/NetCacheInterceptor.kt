package com.even.common.request.interceptor

import com.even.common.utils.NetWorkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author  Created by Even on 2019/8/13
 *  Email: emailtopan@163.com
 *  有网络时缓存拦截器，进行缓存操作
 */
class NetCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val isConnect = NetWorkUtils.isConnect()
        if (isConnect) {
            val response = chain.proceed(request)
            return response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, max-age=" + 60)
                .build()
        }
        //无网络，不做任何拦截直接返回
        return chain.proceed(request)
    }
}