package com.even.kotlinmvvmdemo

import io.reactivex.Observable
import retrofit2.http.GET

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
}