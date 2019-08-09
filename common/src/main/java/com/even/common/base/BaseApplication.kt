package com.even.common.base

import android.app.Application

/**
 * @author  Created by Even on 2019/8/9
 *  Email: emailtopan@163.com
 *
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()


    }

    fun initHttpUtils(hostUrl: String) {


//        RxHttpUtils.setBaseUrl(hostUrl).setOnClient()
    }


}