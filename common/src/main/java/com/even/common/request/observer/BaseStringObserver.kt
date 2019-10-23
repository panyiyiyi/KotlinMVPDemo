package com.even.common.request.observer

import com.even.common.request.exception.ApiException
import com.even.common.request.impl.IStringSubscriber
import com.even.common.request.utils.RxJavaManagerUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author  Created by Even on 2019/8/7
 *  Email: emailtopan@163.com
 *  返回Json格式
 */
abstract class BaseStringObserver : Observer<String>, IStringSubscriber {
    private var mKey: String

    constructor(key: String) {
        mKey = key
    }

    override fun onComplete() {
        doCompleted()
    }

    override fun onSubscribe(d: Disposable) {
        doSubscriber(d)
        RxJavaManagerUtils.addDisposable(mKey, d)
    }

    override fun onNext(t: String) {
        doNext(t)
        doCompleted()
    }

    override fun onError(e: Throwable) {
        val message = ApiException.handleException(e).message
        doFail(message)
        doCompleted()
    }
}