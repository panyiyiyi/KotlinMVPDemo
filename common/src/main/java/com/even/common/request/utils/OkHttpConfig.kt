package com.even.common.request.utils

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 * @author  Created by Even on 2019/8/9
 *  Email: emailtopan@163.com
 *  OkHttp信息配置
 */

class OkHttpConfig {
    var mBuilder = OkHttpClient.Builder()

    //todo 开发中
    companion object Builder {
        private var headerMaps: Map<String, Any>? = null//请求头
        private var isDebug = false  //是否调试模式
        private var isCache = false //是否开启缓存
        private var cachePath = "" //缓存路径
        private var cachMaxSize = 0L //缓存最大值
        private var isSaveCookie = false  //是否保存cookie
        private var readTimeout = 0L  //读超时
        private var writeTimeout = 0L //写超时
        private var connectTimeout = 0L //连接超时
        private var isFile: InputStream? = null  //客户端校验证书
        private var password = ""  //证书密码
        private var certificates: Array<out InputStream>? = null //https证书
        private var interceptors: Array<out Interceptor>? = null //拦截器

        fun setHeaderMaps(headerMaps: Map<String, Any>): Builder {
            this.headerMaps = headerMaps
            return this
        }

        fun setDebug(debug: Boolean): Builder {
            this.isDebug = debug
            return this
        }

        fun setCache(cache: Boolean): Builder {
            this.isCache = cache
            return this
        }

        fun setCachePath(cachePath: String): Builder {
            this.cachePath = cachePath
            return this
        }

        fun setCacheMaxSize(cacheMaxSize: Long): Builder {
            this.cachMaxSize = cacheMaxSize
            return this
        }

        fun setSaveCookie(saveCookie: Boolean): Builder {
            this.isSaveCookie = saveCookie
            return this
        }

        fun setReadTimeout(readTimeout: Long): Builder {
            this.readTimeout = readTimeout
            return this
        }

        fun setWriteTimeout(writeTimeout: Long): Builder {
            this.writeTimeout = writeTimeout
            return this
        }

        fun setConnectTimeout(connectTimeout: Long): Builder {
            this.connectTimeout = connectTimeout
            return this
        }

        fun setIsFile(isFile: InputStream): Builder {
            this.isFile = isFile
            return this
        }

        fun setPassword(password: String): Builder {
            this.password = password
            return this
        }

        fun setCertificates(vararg certificates: InputStream): Builder {
            this.certificates = certificates
            return this
        }

        fun setInterceptors(vararg interceptors: Interceptor): Builder {
            this.interceptors = interceptors
            return this
        }


    }
}