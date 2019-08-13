package com.even.common.request.utils

import android.text.TextUtils
import com.even.common.request.interceptor.LogInterceptor
import com.even.common.request.interceptor.NetCacheInterceptor
import com.even.common.request.interceptor.NoNetCacheInterceptor
import com.even.common.utils.ApplicationUtils
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.InputStream
import java.util.concurrent.TimeUnit

/**
 * @author  Created by Even on 2019/8/9
 *  Email: emailtopan@163.com
 *  OkHttp信息配置
 */

object OkHttpConfig {
    /**
     * 默认缓存大小
     */
    val DEFAULT_CACHE_SIZE = 1024 * 1024 * 100L
    /**
     * 默认超时时间
     */
    val DEFAULT_TIMEOUT = 10L
    /**
     * 默认缓存路径
     */
    val DEFAULT_CACHE_PATH = ApplicationUtils.getInstance().applicationContext.cacheDir.absolutePath
    /**
     * 默认缓存文件夹
     */
    val DEFAULT_DIR_NAME = "rxCache"

    val mBuilder = OkHttpClient.Builder()

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

    fun setHeaderMaps(headerMaps: Map<String, Any>): OkHttpConfig {
        this.headerMaps = headerMaps
        return this
    }

    fun setDebug(debug: Boolean): OkHttpConfig {
        this.isDebug = debug
        return this
    }

    fun setCache(cache: Boolean): OkHttpConfig {
        this.isCache = cache
        return this
    }

    fun setCachePath(cachePath: String): OkHttpConfig {
        this.cachePath = cachePath
        return this
    }

    fun setCacheMaxSize(cacheMaxSize: Long): OkHttpConfig {
        this.cachMaxSize = cacheMaxSize
        return this
    }

    fun setSaveCookie(saveCookie: Boolean): OkHttpConfig {
        this.isSaveCookie = saveCookie
        return this
    }

    fun setReadTimeout(readTimeout: Long): OkHttpConfig {
        this.readTimeout = readTimeout
        return this
    }

    fun setWriteTimeout(writeTimeout: Long): OkHttpConfig {
        this.writeTimeout = writeTimeout
        return this
    }

    fun setConnectTimeout(connectTimeout: Long): OkHttpConfig {
        this.connectTimeout = connectTimeout
        return this
    }

    fun setIsFile(isFile: InputStream): OkHttpConfig {
        this.isFile = isFile
        return this
    }

    fun setPassword(password: String): OkHttpConfig {
        this.password = password
        return this
    }

    fun setCertificates(vararg certificates: InputStream): OkHttpConfig {
        this.certificates = certificates
        return this
    }

    fun setInterceptors(vararg interceptors: Interceptor): OkHttpConfig {
        this.interceptors = interceptors
        return this
    }

    fun build(): OkHttpClient {
        setCacheConfig()
        setSSLConfig()
        addInterceptors()
        setDebugConfig()
        return mBuilder.build()
    }

    /**
     * 设置调试模式不输入返回数据格式
     */
    private fun setDebugConfig() {
        if (isDebug) {
            val logInterceptor = HttpLoggingInterceptor(LogInterceptor())
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            mBuilder.addInterceptor(logInterceptor)
        }
    }


    /**
     * 配置超时时间
     */
    private fun setTimeout() {
        mBuilder.readTimeout(if (readTimeout == 0L) DEFAULT_TIMEOUT else readTimeout, TimeUnit.SECONDS)
        mBuilder.writeTimeout(if (writeTimeout == 0L) DEFAULT_TIMEOUT else writeTimeout, TimeUnit.SECONDS)
        mBuilder.readTimeout(if (connectTimeout == 0L) DEFAULT_TIMEOUT else connectTimeout, TimeUnit.SECONDS)
        mBuilder.retryOnConnectionFailure(true)
    }


    /**
     * 添加配置传递过来得拦截器
     */
    private fun addInterceptors() {
        interceptors?.forEach { mBuilder.addInterceptor(it) }
    }

    /**
     * 配置证书
     */
    private fun setSSLConfig() {
        var sslParam: SSLUtils.SSLParams = if (null == certificates) {
            //信任所有证书
            SSLUtils.getSSLSocketFactory()
        } else {
            if (null != isFile && !TextUtils.isEmpty(password)) {
                //使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务器证书（自签名证书）
                SSLUtils.getSSLSocketFactory(isFile!!, password, *certificates!!)
            } else {
                //使用预埋证书，校验服务器证书（自签名证书）
                SSLUtils.getSSLSocketFactory(*certificates!!)
            }
        }
        mBuilder.sslSocketFactory(sslParam.mSSLSocketFactory, sslParam.trustManager)
    }


    /**
     * 设置缓存
     */
    private fun setCacheConfig() {
        if (isCache) {
            val cache = if (!TextUtils.isEmpty(cachePath) && cachMaxSize > 0) {
                Cache(File(cachePath, DEFAULT_DIR_NAME), cachMaxSize)
            } else {
                Cache(File(DEFAULT_CACHE_PATH, DEFAULT_DIR_NAME), DEFAULT_CACHE_SIZE)
            }
            mBuilder.cache(cache).addInterceptor(NoNetCacheInterceptor())
                .addNetworkInterceptor(NetCacheInterceptor())
        }
    }
}