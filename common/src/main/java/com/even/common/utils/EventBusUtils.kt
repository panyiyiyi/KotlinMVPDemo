package com.even.common.utils

import org.simple.eventbus.EventBus

/**
 * @author  Created by Even on 2019/8/14
 *  Email: emailtopan@163.com
 *
 */
object EventBusUtils {
    /**
     * 注册
     */
    fun register(subscriber: Any) {
        EventBus.getDefault().register(subscriber)
    }

    /**
     * 反注册
     */
    fun unRegister(subscriber: Any) {
        EventBus.getDefault().unregister(subscriber)
    }

    /**
     * 发送数据
     */
    fun <T> sendEvent(data: T, tag: String) {
        EventBus.getDefault().post(data, tag)
    }


}