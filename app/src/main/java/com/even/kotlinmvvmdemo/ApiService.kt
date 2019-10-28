package com.even.kotlinmvvmdemo

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * @author  Created by Even on 2019/8/13
 *  Email: emailtopan@163.com
 *
 */
interface ApiService {
    /**
     * 获取登录界面图片验证吗
     *
     * @return
     */
    @GET("/verificationCode/getVerificationCode")
    fun getImageVerification(): Observable<String>

    /**
     * 获取指定公众号的历史
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    fun getArticleLists(@Path("id") id: String, @Path("page") page: Int): Observable<String>

    @GET("/search_subjects")
    fun getHotVideo(@QueryMap map: MutableMap<String, Any>): Observable<String>
}