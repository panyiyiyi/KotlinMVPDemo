package com.even.common.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.even.common.request.exception.CatchException
import com.even.common.request.utils.OkHttpConfig
import com.even.common.request.utils.RxHttpUtils
import com.even.common.utils.LogUtils
import com.even.common.utils.SpUtils

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
            ARouter.openLog()
            ARouter.openLog()
        }
        ARouter.init(this)
    }

    /**
     * 初始化log日志，默认只有一个tag,设置多个也是只拿第一个
     */
    fun initLog(isDebug: Boolean, vararg logTag: String) {
        if (logTag.isNotEmpty()) {
            LogUtils.init(isDebug, logTag[0])
        } else {
            LogUtils.init(isDebug)
        }
    }

    /**
     * 初始化SpName，未初始化使用Even作为sp得文件名
     */
    fun initSp(spName: String) {
        SpUtils.init(spName)
    }


}