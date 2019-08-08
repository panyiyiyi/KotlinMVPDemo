package com.even.common.request.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author  Created by Even on 2019/8/7
 *  Email: emailtopan@163.com
 *
 */
object RxJavaManagerUtils {
    private var disposableMap: MutableMap<String, CompositeDisposable> = mutableMapOf()

    /**
     * 保存当前key的请求接口
     */
    fun addDisposable(key: String, disposable: Disposable) {
        val keys = disposableMap.keys
        if (keys.contains(key)) {
            val compositeDisposable = disposableMap[key]!!
            compositeDisposable.add(disposable)
        } else {
            val compositeDisposable = CompositeDisposable()
            compositeDisposable.add(disposable)
            disposableMap[key] = compositeDisposable
        }
    }

    /**
     * 取消指定请求
     */
    fun cancelDisposable(key: String) {
        val keys = disposableMap.keys
        if (keys.contains(key)) {
            val compositeDisposable = disposableMap[key]!!
            compositeDisposable.clear()
            disposableMap.remove(key)
        }
    }

    /**
     * 取消所有请求
     */
    fun clearAllDisposable() {
        disposableMap.forEach { it.value.dispose() }
        disposableMap.clear()
    }
}