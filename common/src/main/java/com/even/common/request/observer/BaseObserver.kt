package com.even.common.request.observer

import com.even.common.request.exception.ApiException
import com.even.common.request.impl.ISubscriber
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author  Created by Even on 2019/8/7
 *  Email: emailtopan@163.com
 *
 */
abstract class BaseObserver<T> : Observer<T>, ISubscriber<T> {
    override fun onComplete() {
        doCompleted()
    }

    override fun onSubscribe(d: Disposable) {
        doSubscriber(d)

    }

    override fun onNext(t: T) {
        doNext(t)
    }

    override fun onError(e: Throwable) {
        val message = ApiException.handleException(e).message
        doFail(message)
        doCompleted()
    }
}