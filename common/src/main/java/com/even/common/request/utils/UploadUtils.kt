package com.even.common.request.utils

import io.reactivex.Observable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Retrofit
import java.io.File

/**
 * @author  Created by Even on 2019/8/9
 *  Email: emailtopan@163.com
 *  上传文件工具类
 */
object UploadUtils {
    /**
     * 多图片上传
     */
    fun uploadFiles(
        uploadUrl: String,  //服务器路径
        fileMaps: Map<String, String>,//文件数据
        retrofit: Retrofit
    ): Observable<ResponseBody> {
        return uploadFilesWithParams(uploadUrl, null, fileMaps, retrofit)
    }

    /**
     * 带参数多图片上传
     */
    public fun uploadFilesWithParams(
        uploadUrl: String,  //服务器路径
        paramMaps: Map<String, Any>?,//传递的参数
        fileMaps: Map<String, String>,//传递的文件信息：文件名以及文件路径
        retrofit: Retrofit
    ): Observable<ResponseBody> {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        paramMaps?.forEach { builder.addFormDataPart(it.key, it.value.toString()) }
        fileMaps.forEach {
            val file = File(it.value)
            val fileBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            builder.addFormDataPart(it.key, file.name, fileBody)
        }
        val parts = builder.build().parts
        return retrofit.create(UploadApi::class.java).uploadFiles(uploadUrl, parts)
    }
}