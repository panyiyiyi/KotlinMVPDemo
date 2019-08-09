package com.even.common.request.utils

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Url

/**
 * @author  Created by Even on 2019/8/9
 *  Email: emailtopan@163.com
 *
 */
interface UploadApi {
    /**
     * 上传多个文件
     */
    @Multipart
    @POST
    fun uploadFiles(
        @Url uploadUrl: String,//上传地址
        @Part files: List<MultipartBody.Part>//文件集合
    ): Observable<ResponseBody>

    /**
     * 单文件上传
     */
    fun uploadFile(
        @Url uploadUrl: String,//上传地址
        @Part file: MultipartBody.Part//文件
    ): Observable<ResponseBody>
}