package com.even.common.base

import android.app.Application
import com.even.common.request.exception.CatchException
import com.even.common.request.utils.OkHttpConfig
import com.even.common.request.utils.RxHttpUtils

/**
 * @author  Created by Even on 2019/8/9
 *  Email: emailtopan@163.com
 *
 */
open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CatchException()


    }

    fun initHttpUtils(hostUrl: String, isDebug: Boolean) {
        val build = OkHttpConfig
            .setReadTimeout(10)
            .setWriteTimeout(10)
            .setConnectTimeout(10)
            .setDebug(isDebug)
            .build()
        RxHttpUtils
            .setBaseUrl(hostUrl)
            .setOnClient(build)
            .buildClient()
    }

    fun initRouter(isDebug: Boolean) {
        if (isDebug) {

        }
    }


}