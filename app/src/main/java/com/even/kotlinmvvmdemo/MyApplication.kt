package com.even.kotlinmvvmdemo

import com.even.common.base.BaseApplication

/**
 * @author  Created by Even on 2019/8/5
 *  Email: emailtopan@163.com
 *
 */
class MyApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()

        initHttpUtils("https://movie.douban.com/j/", BuildConfig.DEBUG)
        initRouter(BuildConfig.DEBUG)
        initLog(BuildConfig.DEBUG)
    }

}