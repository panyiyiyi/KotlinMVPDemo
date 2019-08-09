package com.even.common.request.utils

import com.even.common.request.gson.GsonUtils
import com.even.common.request.interceptor.HeaderInterceptor
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

/**
 * @author  Created by Even on 2019/8/9
 *  Email: emailtopan@163.com
 *
 */
object RxHttpUtils {
    /**
     * 请求头拦截器
     */
    private var mHeaderInterceptor: HeaderInterceptor? = null
    private lateinit var mOkHttpClient: OkHttpClient
    private lateinit var mRetrofit: Retrofit

    var mBuilder: Retrofit.Builder = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonUtils.buildGson()))

    /**
     * 设置okhttpClient
     */
    fun setOnClient(onClient: OkHttpClient): RxHttpUtils {
        this.mOkHttpClient = onClient
        mBuilder.client(onClient)
        return this
    }

    /**
     * 设置baseURl
     */
    fun setBaseUrl(baseUrl: String): RxHttpUtils {
        mBuilder.baseUrl(baseUrl)
        return this
    }

    /**
     * 添加请求头
     */
    fun addHeader(key: String, value: String) {
        if (null == mHeaderInterceptor) {
            mHeaderInterceptor = HeaderInterceptor()
            mHeaderInterceptor!!.headerMaps[key] = value
            mOkHttpClient = mOkHttpClient.newBuilder().addInterceptor(mHeaderInterceptor!!).build()

        }

    }

    /**
     * 构建一个完整请求对象
     */
    fun buildClient() {
        mRetrofit = mBuilder.build()
    }

    /**
     * Api请求
     */
    fun <T> createApi(clz: Class<T>): T {
        return mRetrofit.create(clz)
    }

    /**
     * 单图片上传
     */
    fun uploadFile(uploadUrl: String, fileUrl: String): Observable<ResponseBody> {
        val mapOf = mapOf(
            Pair("file", fileUrl)
        )
        return UploadUtils.uploadFiles(uploadUrl, mapOf, mRetrofit)
    }

    /**
     * 单图片上传
     */
    fun uploadFiles(uploadUrl: String, fileMaps: Map<String, String>): Observable<ResponseBody> {
        return UploadUtils.uploadFilesWithParams(uploadUrl, null, fileMaps, mRetrofit)
    }

    /**
     * 带参数多图片上传
     */
    fun uploadFiles(
        uploadUrl: String,
        paramsMaps: Map<String, Any>,
        fileMaps: Map<String, String>
    ): Observable<ResponseBody> {
        return UploadUtils.uploadFilesWithParams(uploadUrl, paramsMaps, fileMaps, mRetrofit)
    }


}