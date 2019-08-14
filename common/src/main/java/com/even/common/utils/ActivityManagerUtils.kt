package com.even.common.utils

import android.app.Activity
import java.util.*

/**
 * @author  Created by Even on 2019/8/6
 *  Email: emailtopan@163.com
 * Activity管理工具类
 */
object ActivityManagerUtils {
    private var activityStacks: Stack<Activity> = Stack()

    fun addActivity(activity: Activity) {
        activityStacks.add(activity)
    }

    /**
     * 获取栈顶Activity
     */
    fun getTopActivity(): Activity {
        return activityStacks.lastElement()
    }

    /**
     * 关闭指定Activity
     */
    fun closeActivity(activity: Activity) {
        activityStacks.remove(activity)
        activity.finish()
    }

    /**
     * 关闭传递过来的类
     */
    fun closeActivity(clazz: Class<*>) {
        activityStacks.forEach {
            if (it.javaClass == clazz) {
                closeActivity(it)
            }
        }
    }

    /**
     * 关闭所有的Activity
     */
    fun closeAllActivity() {
        activityStacks.forEach {
            it?.finish()
        }
        activityStacks.clear()
    }

}