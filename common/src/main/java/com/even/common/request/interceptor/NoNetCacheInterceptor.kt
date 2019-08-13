package com.even.common.request.interceptor

import com.even.common.utils.NetWorkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author  Created by Even on 2019/8/13
 *  Email: emailtopan@163.com
 *  无网络时缓存拦截器
 */
class NoNetCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val isConnect = NetWorkUtils.isConnect()
        if (!isConnect) {
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
            val response = chain.proceed(request)
            return response.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=3600")
                .removeHeader("Pragma")
                .build()
        }
        //有网络，不做任何拦截直接返回
        return chain.proceed(request)
    }
}