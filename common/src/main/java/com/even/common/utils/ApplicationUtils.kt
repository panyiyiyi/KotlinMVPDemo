package com.even.common.utils

import android.annotation.SuppressLint
import android.app.Application
import java.lang.reflect.Method

/**
 * @author  Created by Even on 2019/8/1
 *  Email: emailtopan@163.com
 *
 */
class ApplicationUtils {
    companion object {
        private var application: Application? = null
        fun getInstance(): Application {
            if (null == application) {
                synchronized(ApplicationUtils::class.java) {
                    if (null == application) {
                        ApplicationUtils()
                    }
                }
            }
            return application!!
        }
    }

    @SuppressLint("PrivateApi")
    constructor() {
        val activityThread: Any?
        val threadClass = Class.forName("android.app.ActivityThread") ?: return
        val threadMethod = threadClass.getMethod("currentActivityThread") ?: return
        threadMethod.isAccessible = true
        activityThread = threadMethod.invoke(null)
        val applicationMethod = activityThread.javaClass.getMethod("getApplication") as Method ?: return

        val app = applicationMethod.invoke(activityThread)
        application = app as Application
    }

}