package com.even.common.request.impl

import io.reactivex.disposables.Disposable

/**
 * @author  Created by Even on 2019/8/7
 *  Email: emailtopan@163.com
 *  处理请求返回结果接口，返回已格式化数据
 */
interface ISubscriber<T> {
    /**
     * 订阅回调
     */
    fun doSubscriber(disposable: Disposable)

    /**
     * 失败回调
     */
    fun onFail(errorMsg: String)

    /**
     * 成功回调
     */
    fun doNext(t: T)

    /**
     * 请求晚餐
     */
    fun doCompleted()
}