package com.even.common.request.observer

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author  Created by Even on 2019/8/8
 *  Email: emailtopan@163.com
 *  线程辅助类
 */
object Transformer {

    /**
     * 无参数
     *
     * @param <T>
     * @return
     */
    fun <T> switchSchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe { }.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}